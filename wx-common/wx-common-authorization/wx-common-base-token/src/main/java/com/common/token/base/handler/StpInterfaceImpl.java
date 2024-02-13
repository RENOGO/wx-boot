package com.common.token.base.handler;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollectionUtil;
import com.common.token.base.constants.TokenProperties;
import com.common.token.base.constants.TokenRedisConstants;
import com.wx.common.redis.util.RedisUtil;
import com.wx.usercenter.api.dto.SysPermissionDTO;
import com.wx.usercenter.api.dto.SysRoleDTO;
import com.wx.usercenter.api.service.PermissionServiceApi;
import com.wx.usercenter.api.service.RoleServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author wuweixin
 * @Date 2024/2/12 19:25
 * @Version 1.0
 * @Descritube
 */
public class StpInterfaceImpl implements StpInterface {

    @DubboReference
    private PermissionServiceApi permissionServiceApi;

    @DubboReference
    private RoleServiceApi roleServiceApi;

    @Autowired
    private TokenProperties tokenProperties;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = (String) loginId;
        List<SysPermissionDTO> sysPermissionDTOS = Optional.ofNullable((List<SysPermissionDTO>) RedisUtil.get(TokenRedisConstants.getUserPermission(userId)))
                .orElseGet(() -> {
                    List<SysPermissionDTO> sysPermissionDOS = permissionServiceApi.selectPermissionByUserId(userId);
                    if (sysPermissionDOS == null) {
                        return null;
                    }
                    RedisUtil.set(TokenRedisConstants.getUserPermission(userId), sysPermissionDOS, tokenProperties.getCachePermissionTime());
                    return sysPermissionDOS;
                });
//        if (userId.equals("admin")) {
//            sysPermissionDTOS = new ArrayList<>();
//            SysPermissionDTO sysPermissionDTO = new SysPermissionDTO();
//            sysPermissionDTO.setPermission("admin");
//            sysPermissionDTOS.add(sysPermissionDTO);
//        }
        if (CollectionUtil.isEmpty(sysPermissionDTOS)) {
            return null;
        }
        return sysPermissionDTOS.stream().map(sysPermissionDTO -> sysPermissionDTO.getPermission()).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String userId = (String) loginId;
        List<SysRoleDTO> sysRoleDTOS = Optional.ofNullable((List<SysRoleDTO>) RedisUtil.get(TokenRedisConstants.getUserRole(userId)))
                .orElseGet(() -> {
                    List<SysRoleDTO> role = roleServiceApi.selectRoleByUserId(userId);
                    if (role == null) {
                        return null;
                    }
                    RedisUtil.set(TokenRedisConstants.getUserRole(userId), role, tokenProperties.getCachePermissionTime());
                    return role;
                });
        if (CollectionUtil.isEmpty(sysRoleDTOS)) {
            return null;
        }
        return sysRoleDTOS.stream().map(sysPermissionDTO -> sysPermissionDTO.getRoleKey()).collect(Collectors.toList());
    }
}
