package com.wx.common.security.login;

import com.wx.common.security.user.SecurityUser;
import com.wx.common.security.utils.ResponseUtils;
import com.wx.common.web.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录鉴权成功
 * @author wuweixin
 */
//@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        ResponseUtils.out(response, ResultUtil.genSuccessResult(securityUser.getLoginUser()));
    }
}
