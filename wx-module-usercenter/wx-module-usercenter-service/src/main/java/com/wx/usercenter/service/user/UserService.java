package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.IBaseService;
import com.wx.usercenter.entity.User;

/**
 * @Author wuweixin
 * @Date 2023/9/21 20:56
 * @Version 1.0
 */
public interface UserService extends IBaseService<User> {

    User getUserByUsername(String username);
}
