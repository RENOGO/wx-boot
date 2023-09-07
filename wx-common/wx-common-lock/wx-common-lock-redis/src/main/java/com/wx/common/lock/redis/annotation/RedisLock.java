package com.wx.common.lock.redis.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * redis分布式锁注解
 * 不使用tryLock的话，默认会一直阻塞等锁释放
 * @author wx
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {


    /**
     * 锁名前缀
     *
     * @return
     */
    String lockNamePre() default "";

    /**
     * 如果不使用RedisLockName注解，那么就用这个给锁命名，这种方式的锁名字写死的
     * 如果写了RedisLockName注解又写了这个，优先拿这个注解的锁名
     *
     * @return
     */
    String lockName() default "";

    /**
     * 锁名前后缀
     *
     * @return
     */
    String lockNameSuffix() default "";

    /**
     * 前后缀和锁名拼接在一起的连接符
     *
     * @return
     */
    String separator() default ":";


    /**
     * 等待锁释放的等待时间
     * tryLock 为true的情况下这个参数有效
     *
     * @return
     */
    long waitTime() default 0;


    /**
     * 如果不设置waitTime()，那么一进来拿锁失败就会返回
     * 如果设置了waitTime()，那么waitTime()设置的等待时间到了，还没拿到锁就返回
     * 不配置tryLock，会一直阻塞直到锁释放，所以使用的时候请慎重
     * @return
     */
    boolean tryLock() default false;


    /**
     * 是否开启看门狗，开启看门狗leaseTime这个参数将无效
     * 看门狗默认30秒锁超时，在20秒锁还没释放就再续30秒
     *
     * @return
     */
    boolean watchDog() default false;

    /**
     * 拿到锁后，锁过期时间，默认为5000毫秒(上锁后自动释放锁时间)
     * 如果写了这个，那么看门狗机制就会不执行
     * watchDog为false的情况下这个参数有效
     *
     * @return
     */
    long leaseTime() default 5000;

    /**
     * 时间单位，默认毫秒
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
