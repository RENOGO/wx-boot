package com.wx.authorization.service.login;

import com.common.token.model.LoginUser;
import com.wx.authorization.api.request.LoginRequest;
import com.wx.message.api.dto.CaptchaDTO;

/**
 * @Author wuweixin
 * @Date 2024/2/7 09:28
 * @Version 1.0
 * @Descritube
 */
public interface LoginService {

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    LoginUser loginUser(LoginRequest loginRequest);


    /**
     * 生成图形验证码
     * @param width
     * @param height
     * @return
     */
    CaptchaDTO generateLineCaptcha(int width, int height);


}
