package com.wx.auth.enums;

import com.wx.common.web.IResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2023/10/5 11:53
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ResponseEnums implements IResponseEnum {

    LOGIN_FAIL(2000, "账户或密码错误"),
    CAPTCHA_INVALID(2001, "验证码无效");


    private final int code;
    private final String message;

}
