package com.wx.gateway.configuration;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.wx.common.exception.AuthorizationException;
import com.wx.common.web.BaseResponseEnum;
import com.wx.gateway.service.PermissionService;
import com.wx.usercenter.api.dto.SysPermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/13 19:03
 * @Version 1.0
 * @Descritube
 */
@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class SaTokenConfigure {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private GatewayProperties gatewayProperties;

    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                // /* 拦截全部path */
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    //白名单接口直接跳出,由于是动态配置，所以不在上面的addExclude下配置
                    List<String> whiteList = gatewayProperties.getWhiteList();
                    if (CollectionUtil.isNotEmpty(whiteList) && SaRouter.match(whiteList).isHit) {
                        return;
                    }
                    //查一下用户是否登录了
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                    //超管用户不需要任何接口鉴权
                    List<String> adminRole = gatewayProperties.getAdminRole();
                    if (CollectionUtil.isNotEmpty(adminRole)
                            && StpUtil.hasRoleOr(adminRole.toArray(new String[adminRole.size()]))) {
                        return;
                    }
                    //判断用户是否有当前接口的权限
                    List<SysPermissionDTO> permissions = permissionService.getPermissions();
                    if (CollectionUtil.isNotEmpty(permissions)) {
                        for (SysPermissionDTO permission : permissions) {
                            SaRouterStaff match = SaRouter.match(permission.getApi(), r -> StpUtil.checkPermission(permission.getPermission()));
                            //校验通过则放行
                            if (match.isHit) {
                                return;
                            }
                        }
                    }
                    //默认不配置接口权限，所有已登录的用户都可以访问接口，如果需要做限制所有接口均要配置指定权限才能访问,则指定该配置参数为true
                    if (gatewayProperties.isLimitAllApi()) {
                        throw new AuthorizationException(BaseResponseEnum.UNAUTHORIZED, "用户没有权限访问该接口");
                    }
                });
    }


}
