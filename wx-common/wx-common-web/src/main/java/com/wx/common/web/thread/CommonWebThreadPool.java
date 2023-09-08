package com.wx.common.web.thread;

import com.wx.common.core.thread.AppThreadPool;

/**
 * @Author wx
 * @Date 2023/9/8 13:32
 * @Version 1.0
 */
public class CommonWebThreadPool extends AppThreadPool {

    public static final String COMMON_WEB_THREAD_POOL_NAME = "WxAsyncExecutor";
    public CommonWebThreadPool() {
        super(COMMON_WEB_THREAD_POOL_NAME);
    }
}
