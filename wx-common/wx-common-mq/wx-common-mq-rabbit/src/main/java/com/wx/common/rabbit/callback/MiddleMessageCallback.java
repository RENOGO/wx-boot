package com.wx.common.rabbit.callback;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/10/9 17:07
 * @Version 1.0
 */
@Slf4j
public abstract class MiddleMessageCallback extends AbsMessageCallback {


    @Override
    protected void confirmRetryFail(String exchange, String routingKey, String message, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("exchange", exchange);
        map.put("routingKey", routingKey);
        map.put("message", message);
        map.put("_id", id);
        log.error("消息重试发送至broker失败 res = {}", map);
        if (StrUtil.isNotEmpty(rabbitProperties.getCollectQueue())) {
            rabbitTemplate.convertAndSend(rabbitProperties.getCollectQueue(), map);
        }

    }

    @Override
    protected void returnedMessageRetryFail(ReturnedMessage returned) {
        String message = JSONUtil.toJsonStr(returned);
        log.error("消息没有正确发送至队列 res = {},", message);
        if (StrUtil.isNotEmpty(rabbitProperties.getCollectQueue())) {
            rabbitTemplate.convertAndSend(rabbitProperties.getCollectQueue(), message);
        }

    }

}
