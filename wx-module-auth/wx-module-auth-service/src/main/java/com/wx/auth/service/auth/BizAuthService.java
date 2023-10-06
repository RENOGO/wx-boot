package com.wx.auth.service.auth;

import com.wx.auth.api.req.LoginRequest;
import com.wx.auth.model.vo.LoginResVO;

/**
 * @Author wuweixin
 * @Date 2023/9/21 22:26
 * @Version 1.0
 */
public interface BizAuthService {


    /**
     * 登录
     * @param loginRequest
     * @return
     */
    LoginResVO login(LoginRequest loginRequest);



}
