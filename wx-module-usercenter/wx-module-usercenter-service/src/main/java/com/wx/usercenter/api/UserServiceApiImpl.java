package com.wx.usercenter.api;

import com.wx.usercenter.api.dto.UserDTO;
import com.wx.usercenter.api.req.CreateUserReq;
import com.wx.usercenter.api.service.UserServiceApi;
import com.wx.usercenter.service.user.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:05
 * @Version 1.0
 */
@Service
@DubboService
public class UserServiceApiImpl implements UserServiceApi {

    @Autowired
    private UserService userService;

    @Override
    public UserDTO getUserByUsername(String username) {
        UserDTO userDTO = new UserDTO();
        return (UserDTO) userDTO.convert(userService.getUserByUsername(username));
    }

    @Override
    public String createUser(CreateUserReq req) {
        return userService.createUser(req);
    }
}
