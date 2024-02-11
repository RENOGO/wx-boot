package com.wx.usercenter.constants;

import com.wx.common.web.IResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2024/2/5 09:27
 * @Version 1.0
 * @Descritube
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum implements IResponseEnum {

    /**
     *
     */
    CAPTCHA_CODE_ERROR(10001,"验证码错误"),
    USER_PWD_ERROR(10002,"用户名或密码错误"),

    ;

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;
}
