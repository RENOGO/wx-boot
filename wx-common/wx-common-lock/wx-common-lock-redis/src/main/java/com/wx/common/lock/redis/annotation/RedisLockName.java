package com.wx.common.lock.redis.annotation;

import java.lang.annotation.*;

/**
 * redis分布式锁的key,作用于请求参数上
 * <p>
 * <p>
 * 例子1
 * 寻找Order里面名为userId的参数，只支持String,Integer,Long
 * @author wx
 * @RedisLock(xxxxx)
 * public void deal(@RedisLockName(name = "userId") Order order){
 * <p>
 * }
 * <p>
 * 例子2
 * 只支持String,Integer,Long
 * @RedisLock(xxxxx)
 * public void deal(@RedisLockName String orderId){
 * <p>
 * }
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLockName {

    /**
     * 锁名
     *
     * @return
     */
    String name() default "";
}
