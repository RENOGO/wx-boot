package com.wx.common.web.config;

import com.wx.common.constants.ThreadPoolConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wwx
 * @date 2021/8/31 9:37
 * @description 修改springboot默认的@Async异步注解
 * 因为springboot默认使用的线程池不是一个真正的线程池，还是会一个任务创建一个线程，没有线程复用，线程多了容易OOM
 * 如果有高并发然后需要用到异步线程，建议专门新建一个线程池类，本类的总线程池数默认只有50
 */
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfiguration.class);

    /**
     * 线程池名字前缀
     */
    private final static String THREAD_POOL_NAME_PREFIX = "WxAsyncExecutor-";

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //web应用基本都是IO密集型
        executor.setCorePoolSize(ThreadPoolConstants.DEFAULT_CORE_POOL_SIZE);
        //
        executor.setMaxPoolSize(ThreadPoolConstants.DEFAULT_MAX_POOL_SIZE);
        executor.setQueueCapacity(ThreadPoolConstants.DEFAULT_QUEUE_CAPACITY);
        //拒绝策略：直接丢
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadNamePrefix(THREAD_POOL_NAME_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //关闭线程池时，等待正在执行的任务5秒，超过5秒就关闭了
        executor.setAwaitTerminationSeconds(5);
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> logger.error("异步执行任务 {} , 异常 {}", method, throwable);
    }
}
