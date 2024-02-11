package com.wx.common.security.login;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * 处理登录鉴权
 *
 * @author wuweixin
 */
//@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
