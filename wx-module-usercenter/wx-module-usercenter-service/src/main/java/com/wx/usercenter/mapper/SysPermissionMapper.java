package com.wx.usercenter.mapper;

import com.wx.common.mybatis.mapper.WxBaseMapper;
import com.wx.usercenter.model.dos.SysPermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-12 20:03:29
 */
public interface SysPermissionMapper extends WxBaseMapper<SysPermissionDO> {


    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysPermissionDO> selectPermission(@Param("userId") String userId);

}

