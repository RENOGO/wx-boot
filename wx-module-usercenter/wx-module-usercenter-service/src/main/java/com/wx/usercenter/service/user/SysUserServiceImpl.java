package com.wx.usercenter.service.user;

import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.common.utils.AssertUtil;
import com.wx.usercenter.api.request.CreateUserRequest;
import com.wx.usercenter.enums.ResponseEnums;
import com.wx.usercenter.mapper.SysUserMapper;
import com.wx.usercenter.model.dos.SysUserDO;
import com.wx.usercenter.utils.PasswordUtil;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/9/21 19:36
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {


    @Override
    public SysUserDO getUserByUsername(String username) {
        return selectOne(SysUserDO::getUsername, username);
    }

    @Override
    public String createUser(CreateUserRequest createUserRequest) {
        SysUserDO userDO = getUserByUsername(createUserRequest.getUsername());
        AssertUtil.xAssert(userDO != null, ResponseEnums.ACCOUNT_EXIST);
        userDO = new SysUserDO();
        userDO.convert(createUserRequest);
        userDO.setPassword(PasswordUtil.encode(userDO.getPassword(), null));
        AssertUtil.xAssert(!userDO.insert(), ResponseEnums.REGISTER_FAIL);
        return userDO.getId();
    }

    @Override
    public void delUser(String userId) {
        AssertUtil.xAssert(getBaseMapper().deleteById(userId) <= 0, ResponseEnums.REGISTER_FAIL);
    }


}
