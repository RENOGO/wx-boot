package com.wx.common.security.login;


import com.wx.common.security.constants.SecurityConstant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 专门处理登录认证的过滤器，对/login地址进行拦截处理，controller再写/login将无效
 *
 * @author wuweixin
 */
//@Component
//@ConditionalOnProperty(name = SecurityConstant.SECURITY + "." + SdspSecurityConstant.XAG_SECURITY_ENABLE_CHECK_USER, havingValue = "true")
public class AdminAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

//    @Autowired(required = false)
//    private ILoginHandler ILoginHandler;

    /**
     * @param authenticationManager:             认证管理器
     * @param adminAuthenticationSuccessHandler: 认证成功处理
     * @param adminAuthenticationFailureHandler: 认证失败处理
     */
    public AdminAuthenticationProcessingFilter(CusAuthenticationManager authenticationManager,
                                               AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler,
                                               AdminAuthenticationFailureHandler adminAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher(SecurityConstant.LOGIN_API, SecurityConstant.LOGIN_METHOD));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authRequest;
//        try {
//            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
//            LoginRequest user = JacksonUtil.json2Bean(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), LoginRequest.class);
//            authRequest = new UsernamePasswordAuthenticationToken(user.getAccount(),
//                    user.getPassword(), null);
//            authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));
//        } catch (Exception e) {
//            throw new AuthenticationServiceException(e.getMessage());
//        }
//        return this.getAuthenticationManager().authenticate(authRequest);
        return null;
    }

}
