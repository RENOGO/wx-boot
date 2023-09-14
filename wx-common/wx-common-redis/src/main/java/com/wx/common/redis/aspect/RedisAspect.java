package com.wx.common.redis.aspect;

import com.wx.common.redis.annotation.RedisDelayDel;
import com.wx.common.redis.annotation.RedisDelayDelKey;
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
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author wx
 * @Date 2022/3/7 14:36
 * @Version 1.0
 * 切面
 */
@Component
@Aspect
public class RedisAspect {


    private final static Logger LOGGER = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    private DelayPool delayPool;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.wx.common.redis.annotation.RedisDelayDel)")
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
            RedisDelayDel redisDelayDelete = method.getAnnotation(RedisDelayDel.class);
            String key = redisDelayDelete.key();
            if (ObjectUtils.isEmpty(key)) {
                Object[] args = point.getArgs();
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] parameterAnnotation = parameterAnnotations[i];
                    for (Annotation annotation : parameterAnnotation) {
                        if (annotation.annotationType() == RedisDelayDelKey.class) {
                            RedisDelayDelKey redisDelayDelKey = (RedisDelayDelKey) annotation;
                            Object arg = args[i];
                            if (arg instanceof String) {
                                key = (String) arg;
                            } else {
                                if (!StringUtils.hasText(redisDelayDelKey.key())) {
                                    break;
                                }
                                //不捕获，有问题让开发阶段直接抛出
                                Field declaredField = arg.getClass().getDeclaredField(redisDelayDelKey.key());
                                declaredField.setAccessible(true);
                                key = declaredField.get(arg).toString();
                            }
                            break;
                        }
                    }

                }
            }
            if (ObjectUtils.isEmpty(key)) {
                LOGGER.info("未找到延时双删的key");
                return point.proceed();
            }
            key = getKey(redisDelayDelete, key);
            RedisUtil.del(key);
            LOGGER.info("延时双删1 {}", key);
            Object proceed = point.proceed();
            if (redisDelayDelete.sync()) {
                Thread.sleep(redisDelayDelete.delay());
                RedisUtil.del(key);
                LOGGER.info("延时双删2 {}", key);
            } else {
                delayPool.run(new String[]{key}, redisDelayDelete.delay());
            }
            return proceed;
        } catch (Throwable e) {
            LOGGER.error("延时双删策略失败", e);
        }
        return null;
    }


    private String getKey(RedisDelayDel redisDelayDel, Object key) {
        String prefixSeparator = StringUtils.hasText(redisDelayDel.keyPre()) ? redisDelayDel.separator() : "";
        String suffixSeparator = StringUtils.hasText(redisDelayDel.keySuffix()) ? redisDelayDel.separator() : "";
        return redisDelayDel.keyPre() + prefixSeparator + key + suffixSeparator + redisDelayDel.keySuffix();
    }
}
