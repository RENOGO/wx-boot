package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.usercenter.entity.User;
import com.wx.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/9/21 19:36
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUsername(String username) {
        return selectOne(User::getUsername, username);
    }


}
