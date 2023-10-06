package com.wx.common.validation.validator;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.wx.common.validation.annotation.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author wuweixin
 * @Date 2023/9/26 17:39
 * @Version 1.0
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(s)) {
            return true;
        }
        return Validator.isMobile(s);
    }
}
