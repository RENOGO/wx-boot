package com.wx.common.security.filter;


import cn.hutool.core.util.StrUtil;
import com.wx.common.security.constants.SecurityConstant;
import com.wx.common.security.handler.IAuthoritiesHandler;
import com.wx.common.security.user.LoginUser;
import com.wx.common.security.user.SecurityUser;
import com.wx.common.security.utils.LoginUserUtil;
import com.wx.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @author wwx
 * @date 2021/10/29 17:27
 * @description 自定义的过滤器，对请求接口的token进行拦截校验，判断接口是不是合法
 * 无论接口是不是白名单，这个过滤器都会执行
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory
            .getLogger(TokenAuthenticationFilter.class);


    @Autowired(required = false)
    private IAuthoritiesHandler iAuthoritiesHandler;


    /**
     * 无论接口是不是白名单，这个过滤器都会执行，我们只需要对带token的请求进行校验，不带token的交给spring security去判断是否是白名单
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

//        //先拿请求头的token
        String token = httpServletRequest.getHeader(SecurityConstant.SECURITY_HEADER_TOKEN);
        String appId = httpServletRequest.getHeader(SecurityConstant.SECURITY_HEADER_APP_ID);
        //不包含token或appId的url，直接放过，交给spring security去判断这个接口是不是白名单
        if (StrUtil.isEmpty(token) || StrUtil.isEmpty(appId)) {
            String queryString = httpServletRequest.getQueryString();
            if (StrUtil.isEmpty(queryString)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            Map<String, String> split = StringUtil.query2Map(queryString);
            //请求头没有token那就再拿请求地址的token
            token = split.get(SecurityConstant.SECURITY_QUERY_TOKEN);
            //地址带token的，不需要带bearer了
            if (StrUtil.isEmpty(token)) {
                //还是拿不到token，那就交给security框架去判断这个请求在不在白名单
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            appId = split.get(SecurityConstant.SECURITY_QUERY_APP_ID);
            if (StrUtil.isEmpty(appId)) {
                //同理
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        LoginUser loginUser = LoginUserUtil.getLoginUserInfo();
        if (loginUser == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        SecurityUser securityUser = new SecurityUser(loginUser, iAuthoritiesHandler == null
                ? null
                : iAuthoritiesHandler.getAuthorities(loginUser));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(securityUser,
                        null,
                        securityUser.getAuthorities());
        // 全局注入角色权限信息和登录用户基本信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}
