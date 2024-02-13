package com.common.token.reactor.config;

import cn.dev33.satoken.stp.StpInterface;
import com.common.token.base.constants.TokenProperties;
import com.common.token.base.handler.StpInterfaceImpl;
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


//    @Bean
//    @ConditionalOnProperty(name = PropertiesPre.TOKEN + ".enable-check-permission", havingValue = "true", matchIfMissing = false)
//    public SaTokenConfigure saTokenConfigure() {
//        return new SaTokenConfigure();
//    }


    @Bean
    public StpInterface stpInterface() {
        return new StpInterfaceImpl();
    }

}
