package com.wx.common.redis.annotation;

import java.lang.annotation.*;

/**
 * @Author wx
 * @Date 2022/3/7 14:33
 * @Version 1.0
 * 延时双删
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisDelayDel {


    /**
     * 需要删除的key，如果key是动态变化的，请配合@RedisDelayDelKey注解使用
     * 如果这里的key不为空，并且还使用了@RedisDelayDelKey注解，则默认取当前注解的key
     * 这里不想再用表达式来提高使用者的上手难度
     *
     * @return
     */
    String key() default "";


    /**
     * key前缀
     * @return
     */
    String keyPre() default "";


    /**
     * key后缀
     *
     * @return
     */
    String keySuffix() default "";

    /**
     * 前后缀和锁名拼接在一起的连接符
     *
     * @return
     */
    String separator() default ":";

    /**
     * 延迟时间，单位毫秒，默认500毫秒
     *
     * @return
     */
    long delay() default 500;


    /**
     * 是否是同步删除
     *
     * @return
     */
    boolean sync() default true;

}
