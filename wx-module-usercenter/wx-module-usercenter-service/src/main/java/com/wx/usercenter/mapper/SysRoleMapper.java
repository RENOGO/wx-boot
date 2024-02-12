package com.wx.usercenter.mapper;

import com.wx.common.mybatis.mapper.WxBaseMapper;
import com.wx.usercenter.model.dos.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/12 19:09
 * @Version 1.0
 * @Descritube
 */
public interface SysRoleMapper extends WxBaseMapper<SysRoleDO> {


    /**
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> selectRole(@Param("userId") String userId);

}
