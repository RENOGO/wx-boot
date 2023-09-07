package com.wx.common.core.thread;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.wx.common.constants.ThreadPoolConstants;

import java.util.concurrent.*;

/**
 * @author wwx
 * @date 2021/7/28 15:27
 * @description
 */
public abstract class AppThreadPool extends AbsThreadPool {


    public AppThreadPool(String threadPoolName) {
        super(threadPoolName);
    }


    @Override
    public ThreadFactory threadFactory() {
        return  ThreadFactoryBuilder.create().setNamePrefix(threadPoolName).build();
    }

    /**
     * 默认队列大小
     * @return
     */
    @Override
    public BlockingQueue<Runnable> workQueue() {
        return new LinkedBlockingQueue<>(ThreadPoolConstants.DEFAULT_QUEUE_CAPACITY);
    }

    @Override
    public RejectedExecutionHandler handler() {
        return new ThreadPoolExecutor.AbortPolicy();
    }

    @Override
    public int corePoolSize() {
        return ThreadPoolConstants.DEFAULT_CORE_POOL_SIZE;
    }

    @Override
    public int maximumPoolSize() {
        return ThreadPoolConstants.DEFAULT_MAX_POOL_SIZE;
    }

    @Override
    public TimeUnit unit() {
        return TimeUnit.SECONDS;
    }

    @Override
    public long keepAliveTime() {
        //线程池本身也就默认60S,满足大部分场景
        return ThreadPoolConstants.DEFAULT_KEEP_ALIVE;
    }
}
