package com.wx.auth.service.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.auth.api.dto.CreateAppDTO;
import com.wx.auth.model.dos.AuthAppDO;
import com.wx.common.enums.CommonStatusEnum;

/**
 * @Author wuweixin
 * @Date 2023/10/6 17:35
 * @Version 1.0
 */
public interface AuthAppService {

    /**
     * @param current
     * @param page
     * @return
     */
    Page<AuthAppDO> queryPage(int current, int page);

    /**
     * @param appId
     * @return
     */
    AuthAppDO getAppByAppId(String appId);

    /**
     * 创建应用
     *
     * @param createAppDTO
     */
    String createApp(CreateAppDTO createAppDTO);

    /**
     * 更新应用
     *
     * @param appName
     * @param commonStatusEnum
     */
    void updateApp(String appName, CommonStatusEnum commonStatusEnum);


    /**
     * 删除应用
     *
     * @param id
     */
    void delApp(String id);

}
