package com.wx.common.log.annotation;

import java.lang.annotation.*;


/**
 * 本注解作用于controller的方法
 * @author wx
 */
@Deprecated
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 是否存入exception信息
     * @return
     */
    boolean saveException() default true;

    /**
     * 是否存入请求内容
     * @return
     */
    boolean saveRequest() default true;

    /**
     * 是否存入响应内容
     * @return
     */
    boolean saveResponse() default true;
}
