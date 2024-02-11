package com.wx.common.rabbit.config;

import com.wx.common.rabbit.callback.AbsMessageCallback;
import com.wx.common.rabbit.callback.WxMessageCallback;
import com.wx.common.rabbit.template.WxRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wuweixin
 * @Date 2023/10/9 14:12
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(RabbitSenderProperties.class)
public class WxRabbitConfig {

    /**
     * prefix + name = wx-boot.rabbit.confirm 属性
     * havingValue = true 代表 wx-boot.rabbit.confirm的value为true的时候才会注入
     * matchIfMissing = true 代表没有wx-boot.rabbit.confirm属性也会注入
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "wx-boot.rabbit.sender", name = "confirm", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public AbsMessageCallback confirmMessageSender() {
        return new WxMessageCallback();
    }

    @Bean
    @ConditionalOnProperty(prefix = "wx-boot.rabbit.sender", name = "confirm", havingValue = "true", matchIfMissing = true)
    public WxRabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, AbsMessageCallback callback) {
        WxRabbitTemplate rabbitTemplate = new WxRabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //returnsCallback
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(callback);
        //confirm
        rabbitTemplate.setConfirmCallback(callback);
        return rabbitTemplate;
    }
}
