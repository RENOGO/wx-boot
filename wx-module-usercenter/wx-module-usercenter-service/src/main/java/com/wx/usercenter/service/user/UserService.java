package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.IBaseService;
import com.wx.usercenter.api.req.CreateUserReq;
import com.wx.usercenter.model.dos.UserDO;

/**
 * @Author wuweixin
 * @Date 2023/9/21 20:56
 * @Version 1.0
 */
public interface UserService extends IBaseService<UserDO> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UserDO getUserByUsername(String username);


    /**
     * 创建用户
     * @param createUserReq
     * @return
     */
    String createUser(CreateUserReq createUserReq);


    /**
     * 删除用户
     * @param userId
     */
    void delUser(String userId);

}
