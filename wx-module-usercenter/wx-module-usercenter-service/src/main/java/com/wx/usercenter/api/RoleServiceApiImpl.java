package com.wx.usercenter.api;

import cn.hutool.core.collection.CollectionUtil;
import com.wx.usercenter.api.dto.SysRoleDTO;
import com.wx.usercenter.api.service.RoleServiceApi;
import com.wx.usercenter.model.dos.SysRoleDO;
import com.wx.usercenter.service.role.SysRoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wuweixin
 * @Date 2024/2/12 22:21
 * @Version 1.0
 * @Descritube
 */
@Service
@DubboService
public class RoleServiceApiImpl implements RoleServiceApi {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<SysRoleDTO> selectRoleByUserId(String userId) {
        List<SysRoleDO> sysRoleDOS = sysRoleService.selectRoleByUserId(userId);
        if (CollectionUtil.isEmpty(sysRoleDOS)) {
            return null;
        }
        return sysRoleDOS.stream().map(sysRoleDO -> {
            SysRoleDTO sysRoleDTO = new SysRoleDTO();
            return (SysRoleDTO) sysRoleDTO.convert(sysRoleDO);
        }).collect(Collectors.toList());
    }
}
