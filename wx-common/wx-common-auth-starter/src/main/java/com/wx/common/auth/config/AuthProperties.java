package com.wx.common.auth.config;

import com.wx.common.constants.PropertiesPre;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author wuweixin
 * @Date 2023/10/6 13:44
 * @Version 1.0
 */
@Data
@ConfigurationProperties(PropertiesPre.AUTH)
public class AuthProperties {

    private String key;

    private String moduleName;

    private long expired;
}
