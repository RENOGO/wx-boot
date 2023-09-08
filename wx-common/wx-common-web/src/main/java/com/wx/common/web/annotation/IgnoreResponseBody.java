package com.wx.common.web.annotation;

import java.lang.annotation.*;

/**
 * @author wx
 * @date 2021/9/30 17:18
 * @description 作用于controller
 * 被注解的controller
 * 返回体将不被同一响应格式拦截
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponseBody {
}
