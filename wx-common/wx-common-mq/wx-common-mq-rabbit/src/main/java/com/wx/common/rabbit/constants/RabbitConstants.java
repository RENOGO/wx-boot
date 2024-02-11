package com.wx.common.rabbit.constants;

/**
 * @Author wuweixin
 * @Date 2023/10/9 13:41
 * @Version 1.0
 */
public class RabbitConstants {

    public static final String RABBIT_CONFIRM_MSG = "rabbit:confirm_msg:";

    public static final long RABBIT_EXPIRE = 1000 * 60 * 60;

    public static final int MAX_RETRY_COUNT = 3;
}
