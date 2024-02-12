package com.wx.usercenter.api.service;

import com.wx.usercenter.api.dto.SysRoleDTO;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/12 22:18
 * @Version 1.0
 * @Descritube
 */
public interface RoleServiceApi {
    /**
     *
     * @param userId
     * @return
     */
    List<SysRoleDTO> selectRoleByUserId(String userId);
}
