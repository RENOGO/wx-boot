package com.common.token.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author wuweixin
 * @Date 2024/2/11 20:49
 * @Version 1.0
 * @Descritube 默认拦截器,这里是每个服务自己进行鉴权的，暂时不需要，外部网关会做鉴权，这里先预留着
 */
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login");
    }



}
