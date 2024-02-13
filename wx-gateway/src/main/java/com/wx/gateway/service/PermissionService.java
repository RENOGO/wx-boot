package com.wx.gateway.service;

import com.wx.common.redis.util.RedisUtil;
import com.wx.gateway.configuration.GatewayProperties;
import com.wx.gateway.constants.GatewayRedisConstants;
import com.wx.usercenter.api.dto.SysPermissionDTO;
import com.wx.usercenter.api.service.PermissionServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @Author wuweixin
 * @Date 2024/2/13 19:51
 * @Version 1.0
 * @Descritube
 */
@Service
public class PermissionService {


    @DubboReference
    private PermissionServiceApi permissionServiceApi;

    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     *
     * @return
     */
    public List<SysPermissionDTO> getPermissions() {
        return Optional.ofNullable((List<SysPermissionDTO>) RedisUtil.get(GatewayRedisConstants.PERMISSIONS))
                .orElseGet(new Supplier<List<SysPermissionDTO>>() {
            @Override
            public List<SysPermissionDTO> get() {
                List<SysPermissionDTO> sysPermissionDTOS = permissionServiceApi.selectPermissionByUserId(null);
                if (sysPermissionDTOS == null) {
                    return null;
                }
                RedisUtil.set(GatewayRedisConstants.PERMISSIONS, sysPermissionDTOS, gatewayProperties.getCachePermissionTime());
                return sysPermissionDTOS;
            }
        });

    }

}
