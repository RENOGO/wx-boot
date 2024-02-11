package com.wx.common.rabbit.callback;

import cn.hutool.json.JSONUtil;
import com.wx.common.rabbit.config.RabbitSenderProperties;
import com.wx.common.rabbit.redis.RabbitRedis;
import com.wx.common.rabbit.template.WxRabbitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/10/9 13:24
 * @Version 1.0
 */
@Slf4j
public abstract class AbsMessageCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Lazy
    @Autowired
    protected WxRabbitTemplate rabbitTemplate;

    @Autowired
    protected RabbitSenderProperties rabbitProperties;


    /**
     * 消息没有正确发到队列
     *
     * @param returned the returned message and metadata.
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
//        Message message = returned.getMessage();
//        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        returnedMessageRetryFail(returned);


    }

    /**
     * @param correlationData correlation data for the callback.
     * @param b               true for ack, false for nack
     * @param s               An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            //只要是消息成功发送到broker，都会是true，队列找不到仍是true，所以需要另一个callback处理
            RabbitRedis.del(correlationData.getId());
            return;
        }
        Map<String, Object> msg = RabbitRedis.getMsg(correlationData.getId());
        Integer retryCount = (Integer) msg.get(RabbitRedis.RETRY_COUNT);
        if (retryCount == null) {
            return;
        }
        String message = (String) msg.get(RabbitRedis.MESSAGE);
        retryCount++;
        rabbitTemplate.retry(retryCount);
        String exchange = (String) msg.get(RabbitRedis.EXCHANGE);
        String routingKey = (String) msg.get(RabbitRedis.ROUTING_KEY);
        if (retryCount > rabbitProperties.getMaxRetryCount()) {
            confirmRetryFail(exchange, routingKey, message, correlationData.getId());
            return;
        }
        log.error("消息发送至broker失败重试 id = {},retry = {},msg = {} ", correlationData.getId(), retryCount, JSONUtil.toJsonStr(msg));
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

    }

    /**
     * 重试失败回调
     *
     * @param exchange
     * @param routingKey
     * @param message
     * @param id
     */
    protected abstract void confirmRetryFail(String exchange, String routingKey, String message, String id);


    protected abstract void returnedMessageRetryFail(ReturnedMessage returned);

}
