package com.wx.common.auth.config;

import com.wx.common.auth.service.TokenService;
import com.wx.common.auth.utils.LoginUserUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wuweixin
 * @Date 2023/10/6 15:17
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AutoConfiguration {


    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public LoginUserUtil userUtil() {
        return new LoginUserUtil();
    }

}
