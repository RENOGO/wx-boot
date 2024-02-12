package com.wx.usercenter.api.service;

import com.wx.usercenter.api.dto.SysUserDTO;
import com.wx.usercenter.api.request.CreateUserRequest;

/**
 * @Author wuweixin
 * @Date 2023/9/21 20:56
 * @Version 1.0
 */
public interface UserServiceApi {

    /**
     *
     * @param username
     * @return
     */
    SysUserDTO getUserByUsername(String username);

    String createUser(CreateUserRequest req);

}
