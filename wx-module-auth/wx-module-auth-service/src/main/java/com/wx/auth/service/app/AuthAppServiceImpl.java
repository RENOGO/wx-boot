package com.wx.auth.service.app;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.auth.api.dto.CreateAppDTO;
import com.wx.auth.mapper.AuhAppMapper;
import com.wx.auth.model.dos.AuthAppDO;
import com.wx.common.enums.CommonStatusEnum;
import com.wx.common.mybatis.base.BaseServiceImpl;
import com.wx.common.utils.AssertUtil;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/10/6 17:35
 * @Version 1.0
 */
@Service
public class AuthAppServiceImpl extends BaseServiceImpl<AuhAppMapper, AuthAppDO> implements AuthAppService {


    @Override
    public Page<AuthAppDO> queryPage(int current, int page) {
        return selectPage(current, page, new LambdaQueryWrapper<AuthAppDO>()
                .orderByAsc(AuthAppDO::getAppType)
                .orderByDesc(AuthAppDO::getCreateTime));
    }

    @Override
    public AuthAppDO getAppByAppId(String appId) {
        //todo 加缓存+处理穿透
        return selectOne(AuthAppDO::getAppId, appId);
    }

    @Override
    public String createApp(CreateAppDTO createAppDTO) {
        AuthAppDO appDO = new AuthAppDO();
        appDO.convert(createAppDTO);
        appDO.setAppId(IdUtil.simpleUUID());
        AssertUtil.operateFailed(!appDO.insert());
        return appDO.getAppId();
    }

    @Override
    public void updateApp(String appName, CommonStatusEnum commonStatusEnum) {

    }

    @Override
    public void delApp(String id) {

    }
}
