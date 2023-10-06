package com.wx.auth.model.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.auth.api.enums.AppTypeEnum;
import com.wx.common.enums.CommonStatusEnum;
import com.wx.common.mybatis.base.BaseDO;
import lombok.Data;

/**
 * @Author wuweixin
 * @Date 2023/10/6 17:31
 * @Version 1.0
 */
@Data
@TableName("auth_app")
public class AuthAppDO extends BaseDO<AuthAppDO> {

    private String appName;
    private String appId;
    private CommonStatusEnum status;
    private AppTypeEnum appType;
    /**
     * jwt秘钥
     */
    private String jwtKey;
    /**
     * token过期时间，单位毫秒
     */
    private Long tokenExpired;
    public static final String HEADER_APP_ID = "x-ca-appId";


}
