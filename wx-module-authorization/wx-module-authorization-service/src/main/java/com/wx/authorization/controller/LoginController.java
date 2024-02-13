package com.wx.authorization.controller;

import com.common.token.base.model.LoginUser;
import com.wx.authorization.api.request.LoginRequest;
import com.wx.authorization.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wuweixin
 * @Date 2024/2/11 20:25
 * @Version 1.0
 * @Descritube
 */
@RestController
@Api(tags = "认证模块")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public LoginUser login(@Validated @RequestBody LoginRequest loginRequest) {
        return loginService.loginUser(loginRequest);
    }


}
