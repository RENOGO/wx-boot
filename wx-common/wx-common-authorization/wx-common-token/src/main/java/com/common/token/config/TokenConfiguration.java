package com.common.token.config;

import cn.dev33.satoken.stp.StpInterface;
import com.common.token.base.constants.TokenProperties;
import com.common.token.base.handler.StpInterfaceImpl;
import com.wx.common.constants.PropertiesPre;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wuweixin
 * @Date 2024/2/12 19:31
 * @Version 1.0
 * @Descritube
 */
@EnableConfigurationProperties(TokenProperties.class)
@Configuration
public class TokenConfiguration {


    @Bean
    @ConditionalOnProperty(name = PropertiesPre.TOKEN + ".enable-check-permission", havingValue = "true", matchIfMissing = false)
    public SaTokenConfigure saTokenConfigure() {
        return new SaTokenConfigure();
    }


    @Bean
    @ConditionalOnBean(SaTokenConfigure.class)
    public StpInterface stpInterface() {
        return new StpInterfaceImpl();
    }

}
