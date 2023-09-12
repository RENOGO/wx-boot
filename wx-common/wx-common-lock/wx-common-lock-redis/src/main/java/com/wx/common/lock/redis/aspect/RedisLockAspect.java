package com.wx.common.lock.redis.aspect;

import com.wx.common.lock.redis.annotation.RedisLock;
import com.wx.common.lock.redis.annotation.RedisLockName;
import com.wx.common.lock.redis.utils.RedissonLockUtil;
import jodd.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Aspect
@Component
public class RedisLockAspect {

    private static final String REDISSON_LOCK_PRE = "redisson_lock:";
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockAspect.class);


    /**
     * @param joinPoint
     * @param redisLock
     * @return
     * @throws Throwable
     */
    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        RedisLockName redisLockNameAnn = null;
        Object lockObject = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Object arg = null;

        //找RedisLockName注解+被这个注解修饰的参数
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
        for (int i = 0, redisLockNameIndex = -1; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == RedisLockName.class) {
                    redisLockNameAnn = (RedisLockName) annotation;
                    redisLockNameIndex = i;
                    break;
                }
            }
            if (redisLockNameIndex != -1) {
                arg = joinPoint.getArgs()[redisLockNameIndex];
                break;
            }
        }


        if (redisLockNameAnn == null && StringUtils.isEmpty(redisLock.lockName())) {
            throw new RuntimeException("没有配置任何redisLockName");
        }

        //如果redisLock.lockName()没有内容，那就找RedisLockName注解上的内容
        if (StringUtils.isEmpty(redisLock.lockName())) {
            //RedisLockName作用于基本类型/包装类上
            if (StringUtil.isEmpty(redisLockNameAnn.name())) {
                if (!isLegalBaseType(arg)) {
                    throw new RuntimeException("数据类型为Bean的参数，redisLockName的name参数不能为空");
                }
                lockObject = arg;
            }
            //RedisLockName作用于类上，需要写明哪个属性的值作为锁名
            if (lockObject == null) {
                //这里不捕获了，找不到就直接让他异常出来，开发阶段就应该处理的问题，不会出现在线上
                Field declaredField = arg.getClass().getDeclaredField(redisLockNameAnn.name());
                declaredField.setAccessible(true);
                lockObject = declaredField.get(arg);
            }
        } else {
            lockObject = redisLock.lockName();
        }

        long startTime = System.currentTimeMillis();

        String lockName = getRedissonKey(redisLock, lockObject);
        boolean lockSuccess;
        if (redisLock.tryLock()) {
            if (redisLock.watchDog()) {
                lockSuccess = RedissonLockUtil.tryLock(lockName, redisLock.timeUnit(), redisLock.waitTime());
            } else {
                lockSuccess = RedissonLockUtil.tryLock(lockName, redisLock.timeUnit(), redisLock.waitTime(), redisLock.leaseTime());
            }
        } else {
            RLock lock = null;
            if (redisLock.watchDog()) {
                lock = RedissonLockUtil.lock(lockName);
            } else {
                lock = RedissonLockUtil.lock(lockName, redisLock.timeUnit(), redisLock.leaseTime());
            }
            lockSuccess =  lock.isLocked();
        }
        LOGGER.info("threadId={}: redisson尝试获取锁，lockSuccess={}，method={},redisson_key={}", Thread.currentThread().getId(), lockSuccess, joinPoint.getTarget().getClass().getName() + "$" + methodSignature.getMethod().getName(), lockName);
        //抢不到锁就退出
        //todo 开一个扩展点自定义退出逻辑
        if (!lockSuccess) {
            throw new RuntimeException("访问人数过多，请稍后重试");
        }
        Object result = null;
        try {
            //执行方法
            result = joinPoint.proceed();
        } finally {
            RedissonLockUtil.unlock(lockName);
            //注意！在打印日志的时候，有可能会出现当前线程锁释放的日志还没出来，其他线程拿锁的日志就先出,这是有可能出现的，不是bug
            LOGGER.info("threadId={}: redisson释放了锁,method={},redisson_key={},耗时={}", Thread.currentThread().getId(), joinPoint.getTarget().getClass().getName() + "$" + methodSignature.getMethod().getName(), lockName, System.currentTimeMillis() - startTime);
        }
        return result;
    }

    private String getRedissonKey(RedisLock redisLock, Object lockObject) {
        String prefixSeparator = StringUtil.isEmpty(redisLock.lockNamePre()) ? "" : redisLock.separator();
        String suffixSeparator = StringUtil.isEmpty(redisLock.lockNameSuffix()) ? "" : redisLock.separator();
        return REDISSON_LOCK_PRE + redisLock.lockNamePre() + prefixSeparator + lockObject + suffixSeparator + redisLock.lockNameSuffix();
    }

    private boolean isLegalBaseType(Object o) {
        return o instanceof Integer || o instanceof String || o instanceof Long;
    }


}
