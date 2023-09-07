package com.wx.common.constants;

/**
 * @author wx
 * @version 1.0.0
 * @ClassName ThreadPoolConstants.java
 * @Description TODO
 * @createTime 2022年05月26日 14:29:00
 */
public class ThreadPoolConstants {

    /**
     * web应用基本是IO密集型
     *  Runtime.getRuntime().availableProcessors()获取的是可用的cpu资源,不是实际的物理cpu
     */
    public static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 最大线程数
     */
    public static final int DEFAULT_MAX_POOL_SIZE = DEFAULT_CORE_POOL_SIZE * 2;

    /**
     * 默认最大队列数
     */
    public static final int DEFAULT_QUEUE_CAPACITY = 64;

    /**
     * 线程空闲最大时间，空闲超过这个时间则会被回收，单位秒
     */
    public static final int DEFAULT_KEEP_ALIVE = 60;



}
