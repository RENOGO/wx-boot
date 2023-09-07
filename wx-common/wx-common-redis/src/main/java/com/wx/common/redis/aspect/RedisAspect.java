package com.wx.common.redis.aspect;

import com.wx.common.redis.annotation.RedisDelayDoubleDelete;
import com.wx.common.redis.pool.DelayPool;
import com.wx.common.redis.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @Author wuweixin
 * @Date 2022/3/7 14:36
 * @Version 1.0
 * 切面
 */
@Component
@Aspect
public class RedisAspect {

    private final static Logger logger = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    private DelayPool delayPool;
    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.wx.common.redis.annotation.RedisDelayDoubleDelete)")
    public void redisDelayDoubleDelete() {

    }


    /**
     * 通知，处理延时双删
     *
     * @param point
     * @return
     */
    @Around("redisDelayDoubleDelete()")
    public Object redisDelayDoubleDeleteNotice(ProceedingJoinPoint point) {
        try {
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            // 获取RedisCache注解
            RedisDelayDoubleDelete redisDelayDoubleDelete = method.getAnnotation(RedisDelayDoubleDelete.class);
            String[] key = redisDelayDoubleDelete.key();
            if (ObjectUtils.isEmpty(key)) {
                return point.proceed();
            }
            RedisUtil.del(key);
            Object proceed = point.proceed();
            if (redisDelayDoubleDelete.sync()) {
                Thread.sleep(redisDelayDoubleDelete.delay());
                RedisUtil.del(key);
            } else {
                delayPool.run(key,redisDelayDoubleDelete.delay());
            }
            return proceed;
        } catch (Throwable e) {
            logger.error("延时双删策略失败", e);
        }
        return null;
    }


}
