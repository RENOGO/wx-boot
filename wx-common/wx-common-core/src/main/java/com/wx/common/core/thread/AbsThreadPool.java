package com.wx.common.core.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author wx
 * @date 2021/7/28 14:12
 * @description
 */
public abstract class AbsThreadPool {

    private final static Logger logger = LoggerFactory.getLogger(AbsThreadPool.class);

    private ExecutorService executorService;
    /**
     * 要求一定要声明线程池的名称
     */
    public String threadPoolName;

    public AbsThreadPool(String threadPoolName) {
        this.threadPoolName = threadPoolName;
        try {
            executorService = new ThreadPoolExecutor(corePoolSize(),
                    maximumPoolSize(),
                    keepAliveTime(),
                    unit(),
                    workQueue(),
                    threadFactory(),
                    handler());
        } catch (Exception e) {
            logger.error("线程池初始化失败 {}", threadPoolName, e);
        }
    }

    public void run(Runnable command) {
        getExecutorService().execute(command);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }


    public void removeRunnable(Runnable runnable) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) getExecutorService();
        threadPoolExecutor.remove(runnable);
    }

    public abstract ThreadFactory threadFactory();

    public abstract int corePoolSize();

    public abstract int maximumPoolSize();

    public abstract long keepAliveTime();

    public abstract TimeUnit unit();

    public abstract BlockingQueue<Runnable> workQueue();

    public abstract RejectedExecutionHandler handler();

}
