package com.wx.usercenter.api;

import com.wx.usercenter.api.dto.SysUserDTO;
import com.wx.usercenter.api.request.CreateUserRequest;
import com.wx.usercenter.api.service.UserServiceApi;
import com.wx.usercenter.service.user.SysUserService;
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
    private SysUserService sysUserService;

    @Override
    public SysUserDTO getUserByUsername(String username) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        return (SysUserDTO) sysUserDTO.convert(sysUserService.getUserByUsername(username));
    }

    @Override
    public String createUser(CreateUserRequest req) {
        return sysUserService.createUser(req);
    }
}
