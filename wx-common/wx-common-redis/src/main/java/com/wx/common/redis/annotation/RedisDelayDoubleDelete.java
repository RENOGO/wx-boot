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
public @interface RedisDelayDoubleDelete {

    /**
     * 需要删除的key
     * @return
     */
    String[] key();

    /**
     * 延迟时间，单位毫秒，默认500毫秒
     * @return
     */
    long delay() default 500;


    /**
     * 是否是同步删除
     * @return
     */
    boolean sync() default true;

}
