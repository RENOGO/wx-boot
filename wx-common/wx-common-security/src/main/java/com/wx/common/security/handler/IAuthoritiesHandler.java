package com.wx.common.security.handler;

import com.wx.common.security.user.LoginUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author wwx
 * @date 2021/11/23 16:22
 * @description
 */
public interface IAuthoritiesHandler {


    /**
     * 填充当前用户角色
     * @param loginUser
     * @return
     */
    Collection<? extends GrantedAuthority> getAuthorities(LoginUser loginUser);



}
