package com.wx.usercenter.api.service;

import com.wx.usercenter.api.dto.UserDTO;
import com.wx.usercenter.api.req.CreateUserReq;

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
    UserDTO getUserByUsername(String username);

    String createUser(CreateUserReq req);

}
