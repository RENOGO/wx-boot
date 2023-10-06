package com.wx.common.validation.annotation;

import com.wx.common.validation.validator.MobileValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @Author wuweixin
 * @Date 2023/9/26 17:35
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = MobileValidator.class
)
public @interface Mobile {
}
