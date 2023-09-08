package com.wx.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author wx
 * @date 2022/9/30 17:18
 * @description 作用于controller，
 * 被注解的controller
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLog {
}
