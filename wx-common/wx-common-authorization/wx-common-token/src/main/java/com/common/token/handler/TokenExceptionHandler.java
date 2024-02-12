package com.common.token.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.wx.common.web.BaseResponseEnum;
import com.wx.common.web.WebResponse;
import com.wx.common.web.WebResponseGenerator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author wuweixin
 * @Date 2024/2/12 23:02
 * @Version 1.0
 * @Descritube 一般用不到，网关会拦截掉无效的token
 */
@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public WebResponse<?> handlerNotLoginException(NotLoginException nle)
            throws Exception {

        // 打印堆栈，以供调试
        nle.printStackTrace();

        // 判断场景值，定制化异常信息
        String message = "";
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "token 已被冻结";
        }
        else if(nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        }
        else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return WebResponseGenerator.genFailResult(BaseResponseEnum.UNAUTHORIZED, message);
    }
}
