package com.wx.common.security.config;


import com.wx.common.security.auth.AdminAuthenticationEntryPoint;
import com.wx.common.security.filter.TokenAuthenticationFilter;
import com.wx.common.security.handler.LogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wuweixin
 * @Descritube spring security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"com.wx.common.security"})
public class SecurityConfig {


    /**
     * 访问权限认证异常处理
     */
    @Autowired
    private AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;
    /**
     *
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private LogoutHandler logoutHandler;

    private static final String[] DEFAULT_IGNORE_URL = {"/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> ignoreUrls = securityProperties.getIgnoreUrls();
        ignoreUrls = Optional.ofNullable(ignoreUrls).orElse(new ArrayList<>());
        ignoreUrls.addAll(Arrays.asList(DEFAULT_IGNORE_URL));
        http.exceptionHandling()
                // 未登录认证异常
                .authenticationEntryPoint(adminAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //OPTIONS(选项)：查找适用于一个特定网址资源的通讯选择。 在不需执行具体的涉及数据传输的动作情况下，
                // 允许客户端来确定与资源相关的选项以及 / 或者要求， 或是一个服务器的性能
                .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
                .antMatchers("/login").permitAll()
                //这里的白名单路径，不要带上yml里的context-path路径,会无效的
                .antMatchers(CollectionUtils.isEmpty(ignoreUrls) ? new String[]{}
                        : ignoreUrls.toArray(new String[]{}))
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .and()
                //token验证
                .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }
}

