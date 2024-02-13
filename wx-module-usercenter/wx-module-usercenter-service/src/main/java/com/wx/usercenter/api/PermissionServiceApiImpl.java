package com.wx.usercenter.api;

import cn.hutool.core.collection.CollectionUtil;
import com.wx.usercenter.api.dto.SysPermissionDTO;
import com.wx.usercenter.api.service.PermissionServiceApi;
import com.wx.usercenter.model.dos.SysPermissionDO;
import com.wx.usercenter.service.permission.SysPermissionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wuweixin
 * @Date 2024/2/12 21:08
 * @Version 1.0
 * @Descritube
 */
@Service
@DubboService
public class PermissionServiceApiImpl implements PermissionServiceApi {

    @Autowired
    private SysPermissionService sysPermissionService;


    @Override
    public List<SysPermissionDTO> selectPermissionByUserId(String userId) {
        List<SysPermissionDO> sysPermissionDO = sysPermissionService.selectPermissionByUserId(userId);
        if (CollectionUtil.isEmpty(sysPermissionDO)) {
            return null;
        }
        return sysPermissionDO.stream().map(sysPermissionDO1 -> {
            SysPermissionDTO sysPermissionDTO = new SysPermissionDTO();
            return (SysPermissionDTO) sysPermissionDTO.convert(sysPermissionDTO);
        }).collect(Collectors.toList());
    }


}
