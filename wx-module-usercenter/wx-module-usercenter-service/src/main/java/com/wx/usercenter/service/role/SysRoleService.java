package com.wx.usercenter.service.role;

import com.wx.common.mybatis.base.IBaseService;
import com.wx.usercenter.model.dos.SysRoleDO;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/12 21:54
 * @Version 1.0
 * @Descritube
 */
public interface SysRoleService extends IBaseService<SysRoleDO> {

    /**
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> selectRoleByUserId(String userId);

}
