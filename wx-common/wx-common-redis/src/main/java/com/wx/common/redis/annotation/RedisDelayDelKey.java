package com.wx.common.redis.annotation;

import java.lang.annotation.*;

/**
 * @Author wuweixin
 * @Date 2023/9/14 12:15
 * @Version 1.0
 * 作用于包装类和实体类，如果是实体类，key请写明是哪个属性的名称
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisDelayDelKey {

    /**
     * key名称,如果参数是实体类的时候，写命对应属性的名称
     * 如果作用的参数是字符串，这里可以不写
     * @return
     */
    String key() default "";

}
