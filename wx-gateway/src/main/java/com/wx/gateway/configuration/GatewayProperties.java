package com.wx.gateway.configuration;

import com.common.token.base.constants.TokenConstants;
import com.wx.common.constants.PropertiesPre;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/13 19:54
 * @Version 1.0
 * @Descritube
 */
@ConfigurationProperties(prefix = PropertiesPre.GATEWAY)
@Data
public class GatewayProperties {


    /**
     * 缓存权限的时间，单位秒
     */
    private long cachePermissionTime = TokenConstants.DEFAULT_PERMISSION_EXPIRE;

    /**
     * 白名单地址
     */
    private List<String> whiteList;

    /**
     * 超管角色配置，默认所有接口都可以使用
     */
    private List<String> adminRole;

    /**
     * 是否所有接口均要配置权限才允许访问
     */
    private boolean limitAllApi;

}
