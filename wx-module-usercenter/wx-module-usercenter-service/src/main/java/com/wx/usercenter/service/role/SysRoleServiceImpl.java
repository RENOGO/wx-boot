package com.wx.usercenter.service.role;

import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.usercenter.mapper.SysRoleMapper;
import com.wx.usercenter.model.dos.SysRoleDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/12 21:55
 * @Version 1.0
 * @Descritube
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {


    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRoleDO> selectRoleByUserId(String userId) {
//        return Optional.ofNullable((List<SysRoleDO>) RedisUtil.get(RedisConstants.getUserPermission(userId)))
//                .orElseGet(() -> {
//                    List<SysRoleDO> sysPermissionDOS = getBaseMapper().selectRole(userId);
//                    if (sysPermissionDOS == null) {
//                        return null;
//                    }
//                    RedisUtil.set(RedisConstants.getUserPermission(userId), sysPermissionDOS, RedisConstants.DEFAULT_PERMISSION_EXPIRE);
//                    return sysPermissionDOS;
//                });
        return getBaseMapper().selectRole(userId);
    }
}
