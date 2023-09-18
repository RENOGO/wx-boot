package com.wx.common.web.annotation;

import java.lang.annotation.*;

/**
 * @Author wuweixin
 * @Date 2023/9/18 14:36
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExceptionOrder {

    /**
     * 越小级别越高
     * @return
     */
    int order() default 0;

}
