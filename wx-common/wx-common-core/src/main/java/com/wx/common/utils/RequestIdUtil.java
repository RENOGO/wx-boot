package com.wx.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.UUID;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName RequqestIdUtil.java
 * @Description TODO
 * @createTime 2022年11月25日 11:35:00
 */
public class RequestIdUtil {

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    /**
     * 不需要加锁，请确保拿这个requestId的线程是主线程，而不是异步线程去拿requestId
     *
     * @return
     */
    public static String getRequestId() {
        String requestId = REQUEST_ID.get();
        if (StrUtil.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
            REQUEST_ID.set(requestId);
        }
        return requestId;
    }


}
