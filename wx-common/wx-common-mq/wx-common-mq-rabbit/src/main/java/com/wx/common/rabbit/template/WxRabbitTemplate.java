package com.wx.common.rabbit.template;

import com.wx.common.rabbit.config.RabbitSenderProperties;
import com.wx.common.rabbit.redis.RabbitRedis;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author wuweixin
 * @Date 2023/10/9 13:32
 * @Version 1.0
 */
@Primary
public class WxRabbitTemplate extends RabbitTemplate {
    private static final ThreadLocal<Integer> RETRY_THREAD_LOCAL = new ThreadLocal<>();
    @Autowired
    private RabbitSenderProperties rabbitProperties;

    public void retry(int count) {
        RETRY_THREAD_LOCAL.set(count);
    }

    public int getRetryCount() {
        return Optional.ofNullable(RETRY_THREAD_LOCAL.get()).orElse(0);
    }

    /**
     * @param exchange        the name of the exchange
     * @param routingKey      the routing key
     * @param message         a message to send
     * @param correlationData data to correlate publisher confirms.
     * @throws AmqpException
     */
    @Override
    public void send(String exchange, String routingKey, Message message, CorrelationData correlationData) throws AmqpException {
        if (correlationData == null) {
            correlationData = new CorrelationData(UUID.randomUUID().toString());
        }
        RabbitRedis.putMsg(correlationData.getId(), exchange, routingKey, new String(message.getBody(), StandardCharsets.UTF_8), getRetryCount(), rabbitProperties.getMessageExpire());
        super.send(exchange, routingKey, message, correlationData);
        RETRY_THREAD_LOCAL.remove();
    }
}
