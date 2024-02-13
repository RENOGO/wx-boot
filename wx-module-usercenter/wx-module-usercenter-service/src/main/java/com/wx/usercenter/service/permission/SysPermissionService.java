package com.wx.usercenter.service.permission;


import com.wx.common.mybatis.base.IBaseService;
import com.wx.usercenter.model.dos.SysPermissionDO;

import java.util.List;

/**
 * (SysPermission)表服务接口
 *
 * @author makejava
 * @since 2024-02-12 20:03:31
 */
public interface SysPermissionService extends IBaseService<SysPermissionDO> {

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysPermissionDO> selectPermissionByUserId(String userId);


//    /**
//     * 查询所有的权限
//     * @return
//     */
//    List<SysPermissionDO> selectAllPermission();
}

