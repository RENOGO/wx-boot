package com.wx.common.exception;

import com.wx.common.web.IResponseEnum;

/**
 * @Author wuweixin
 * @Date 2024/2/13 22:24
 * @Version 1.0
 * @Descritube
 */
public class AuthorizationException extends BaseException {

    public AuthorizationException(IResponseEnum errorInterface) {
        super(errorInterface);
    }

    public AuthorizationException(IResponseEnum errorInterface, String message) {
        super(errorInterface, message);
    }
}
