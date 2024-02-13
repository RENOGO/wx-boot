package com.wx.usercenter.api.service;

import com.wx.usercenter.api.dto.SysPermissionDTO;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/12 21:06
 * @Version 1.0
 * @Descritube
 */
public interface PermissionServiceApi {

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysPermissionDTO> selectPermissionByUserId(String userId);



}
