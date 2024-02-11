package com.wx.common.rabbit.config;

import com.wx.common.rabbit.constants.RabbitConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author wuweixin
 * @Date 2023/10/9 20:10
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "wx-boot.rabbit.sender")
public class RabbitSenderProperties {

    private Integer maxRetryCount = RabbitConstants.MAX_RETRY_COUNT;

    private Long messageExpire = RabbitConstants.RABBIT_EXPIRE;

    /**
     * 消息是否发送到队列
     */
    private String collectQueue;

    private boolean collectToDb;


}
