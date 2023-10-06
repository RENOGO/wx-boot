package com.wx.usercenter.enums;

import com.wx.common.web.IResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2023/10/2 15:43
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ResponseEnums implements IResponseEnum {

    /**
     *
     */
    ACCOUNT_EXIST(1000, "账户名已被占用，请重试"),
    REGISTER_FAIL(1001, "创建用户失败"),
    DEL_FAIL(1002, "删除用户失败"),
    LOGIN_FAIL(2000, "账户或密码错误"),
    CAPTCHA_INVALID(2001, "验证码无效");


    private final int code;
    private final String message;

}
