package com.wx.common.lock.redis.utils;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * redisson工具类
 */
@Component
public class RedissonLockUtil {

    @Autowired
    private RedissonClient redissonClient;

    private static RedissonLockUtil redissonLockUtil;

    @PostConstruct
    public void init() {
        redissonLockUtil = this;
    }

    /**
     * 获得锁（不是加锁）
     *
     * @param lockName
     * @return
     */
    public static RLock getLock(String lockName) {
        return redissonLockUtil.redissonClient.getLock(lockName);
    }

    /**
     * 1、会一直等占有锁的线程将锁释放
     * 2、这个方法会触发看门狗机制
     * 3、默认占有锁30秒，在20秒的时候锁还没释放，看门狗就会给锁续20秒
     *
     * @param lockName
     * @return
     */
    public static RLock lock(String lockName) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        lock.lock();
        return lock;
    }

    /**
     * 释放锁
     *
     * @param lockName
     */
    public static void unlock(String lockName) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        unlock(lock);
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public static void unlock(RLock lock) {
        //有看门狗的话这一段判断就没必要，这里主要是针对入参的时候有配置占锁多久没有执行结束就释放锁的配置
        if (lock != null && lock.isLocked()) {
            //防止某个线程长时间工作超过锁的时间,然后锁被别的线程拿了，接着把人家还在跑的锁给释放了
            //判断这个锁是不是当前线程创建的，通过判断uuid+线程id判断有没有持有这个lockName
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 1、占有锁多长时间就超时
     * 2、会一直等占有锁的线程将锁释放
     * 3、由于1的存在，所以这个是不带看门狗机制的
     *
     * @param lockName
     * @param leaseTime 占有锁之后，锁超时的时间  单位：秒
     */
    public static RLock lock(String lockName, long leaseTime) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 带超时的锁，会一直等占有锁的线程将锁释放
     *
     * @param lockName
     * @param unit      时间单位
     * @param leaseTime 占有锁之后，锁超时的时间，由于这个参数，所以不带看门狗机制的
     */
    public static RLock lock(String lockName, TimeUnit unit, long leaseTime) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        lock.lock(leaseTime, unit);
        return lock;
    }

    /**
     * 尝试获取锁，不会一直等占有锁的线程将锁释放
     * 由于存在leaseTime，所以不会触发看门狗机制
     *
     * @param lockName
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间  使用这个参数将导致看门狗机制失效
     * @return
     */
    public static boolean tryLock(String lockName, int waitTime, int leaseTime) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁，不会一直等占有锁的线程将锁释放
     * 由于不存在leaseTime，所以会触发看门狗机制
     *
     * @param lockName
     * @param unit     时间单位
     * @param waitTime 最多等待时间
     * @return
     */
    public static boolean tryLock(String lockName,
                                  TimeUnit unit,
                                  long waitTime) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁，不会一直等占有锁的线程将锁释放
     * 由于存在leaseTime，所以不会触发看门狗机制
     *
     * @param lockName
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间  使用这个参数将导致看门狗机制失效
     * @return
     */
    public static boolean tryLock(String lockName,
                                  TimeUnit unit,
                                  long waitTime,
                                  long leaseTime) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }


    /**
     * 尝试获取锁，不会一直等占有锁的线程将锁释放
     * 由于不存在leaseTime，所以会触发看门狗机制
     *
     * @param lockName
     * @param unit     时间单位
     * @param waitTime 最多等待时间
     * @return
     */
    @Deprecated
    public static boolean tryLock(String lockName,
                                  TimeUnit unit,
                                  long waitTime,
                                  Function<RLock, Void> callback) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        callback.apply(lock);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }


    /**
     * 尝试获取锁，不会一直等占有锁的线程将锁释放
     * 由于存在leaseTime，所以不会触发看门狗机制
     *
     * @param lockName
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间  使用这个参数将导致看门狗机制失效
     * @return
     */
    @Deprecated
    public static boolean tryLock(String lockName,
                                  TimeUnit unit,
                                  long waitTime,
                                  long leaseTime,
                                  Function<RLock, Void> callback) {
        RLock lock = redissonLockUtil.redissonClient.getLock(lockName);
        callback.apply(lock);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }


    /**
     * 递增
     *
     * @param key
     * @param delta 要增加几(大于0)
     * @return
     */
    public int incr(String mapName, String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonLockUtil.redissonClient.getMapCache(mapName);
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return mapCache.addAndGet(key, delta);//加1并获取计算后的值
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public int decr(String mapName, String key, int delta) {
        return incr(mapName, key, -delta);
    }
}