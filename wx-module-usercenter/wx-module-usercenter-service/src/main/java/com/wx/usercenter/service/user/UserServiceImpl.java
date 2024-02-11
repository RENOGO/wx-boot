package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.common.security.utils.PasswordUtil;
import com.wx.common.utils.AssertUtil;
import com.wx.usercenter.api.request.CreateUserRequest;
import com.wx.usercenter.enums.ResponseEnums;
import com.wx.usercenter.mapper.UserMapper;
import com.wx.usercenter.model.dos.UserDO;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/9/21 19:36
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO> implements UserService {


    @Override
    public UserDO getUserByUsername(String username) {
        return selectOne(UserDO::getUsername, username);
    }

    @Override
    public String createUser(CreateUserRequest createUserRequest) {
        UserDO user = getUserByUsername(createUserRequest.getUsername());
        AssertUtil.xAssert(user != null, ResponseEnums.ACCOUNT_EXIST);
        user = new UserDO();
        user.convert(createUserRequest);
        user.setPassword(PasswordUtil.encode(user.getPassword(), null));
        AssertUtil.xAssert(!user.insert(), ResponseEnums.REGISTER_FAIL);
        return user.getId();
    }

    @Override
    public void delUser(String userId) {
        AssertUtil.xAssert(getBaseMapper().deleteById(userId) <= 0, ResponseEnums.REGISTER_FAIL);
    }


}
