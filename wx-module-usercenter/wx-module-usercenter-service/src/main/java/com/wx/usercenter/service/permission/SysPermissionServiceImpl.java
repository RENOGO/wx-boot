package com.wx.usercenter.service.permission;

import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.usercenter.mapper.SysPermissionMapper;
import com.wx.usercenter.model.dos.SysPermissionDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysPermission)表服务实现类
 *
 * @author makejava
 * @since 2024-02-12 20:03:31
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermissionDO> implements SysPermissionService {

    @Override
    public List<SysPermissionDO> selectPermissionByUserId(String userId) {
//        return Optional.ofNullable((List<SysPermissionDO>) RedisUtil.get(RedisConstants.getUserPermission(userId)))
//                .orElseGet(() -> {
//                    List<SysPermissionDO> sysPermissionDOS = getBaseMapper().selectPermission(userId);
//                    if (sysPermissionDOS == null) {
//                        return null;
//                    }
//                    RedisUtil.set(RedisConstants.getUserPermission(userId), sysPermissionDOS, RedisConstants.DEFAULT_PERMISSION_EXPIRE);
//                    return sysPermissionDOS;
//                });
        return getBaseMapper().selectPermission(userId);
    }


}

