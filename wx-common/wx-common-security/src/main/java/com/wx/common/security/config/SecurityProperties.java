package com.wx.common.security.config;

import com.wx.common.constants.PropertiesPre;
import com.wx.common.security.constants.SecurityConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuweixin
 */
@Component
@ConfigurationProperties(PropertiesPre.SECURITY)
public class SecurityProperties {

    /**
     * 忽略不拦截的url
     */
    private List<String> ignoreUrls;

    private String jwtKey;

    private String appId;


    /**
     * token过期时间，单位秒
     */
    private Long expires = SecurityConstant.REDIS_TOKEN_EXPIRED;

    /**
     * 用户信息在redis中存放的时间，单位秒
     */
    private Long refreshExpires = SecurityConstant.REDIS_TOKEN_EXPIRED;

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getJwtKey() {
        return jwtKey;
    }

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
