package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.IBaseService;
import com.wx.usercenter.api.request.CreateUserRequest;
import com.wx.usercenter.model.dos.SysUserDO;

/**
 * @Author wuweixin
 * @Date 2023/9/21 20:56
 * @Version 1.0
 */
public interface SysUserService extends IBaseService<SysUserDO> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    SysUserDO getUserByUsername(String username);


    /**
     * 创建用户
     * @param createUserRequest
     * @return
     */
    String createUser(CreateUserRequest createUserRequest);


    /**
     * 删除用户
     * @param userId
     */
    void delUser(String userId);

}
