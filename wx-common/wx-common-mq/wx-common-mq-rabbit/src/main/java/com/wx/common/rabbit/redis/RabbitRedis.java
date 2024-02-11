package com.wx.common.rabbit.redis;

import com.wx.common.rabbit.constants.RabbitConstants;
import com.wx.common.redis.util.RedisUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author wuweixin
 * @Date 2023/10/9 13:49
 * @Version 1.0
 */
public class RabbitRedis {

    public static final String MESSAGE = "message";
    public static final String EXCHANGE = "exchange";
    public static final String ROUTING_KEY = "routingKey";

    public static final String RETRY_COUNT = "retryCount";

    public static void del(String id) {
        RedisUtil.del(getConfirmId(id));
    }

    public static void putMsg(String id, String exchange, String routingKey, String message, int retryCount, long expire) {
        Map<String, Object> msg = new HashMap<>();
        msg.put(MESSAGE, message);
        msg.put(EXCHANGE, exchange);
        msg.put(ROUTING_KEY, routingKey);
        msg.put(RETRY_COUNT, retryCount);
        RedisUtil.set(getConfirmId(id), msg, expire, TimeUnit.MILLISECONDS);
    }

    public static Map<String, Object> getMsg(String id) {
        return (Map<String, Object>) RedisUtil.get(getConfirmId(id));
    }


    public static String getConfirmId(String id) {
        return RabbitConstants.RABBIT_CONFIRM_MSG + id;
    }

}
