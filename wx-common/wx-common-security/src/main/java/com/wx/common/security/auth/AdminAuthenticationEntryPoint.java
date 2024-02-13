package com.wx.common.security.auth;


import cn.hutool.http.HttpStatus;
import com.wx.common.security.utils.ResponseUtils;
import com.wx.common.web.BaseResponseEnum;
import com.wx.common.exception.BusinessException;
import com.wx.common.web.WebResponse;
import com.wx.common.web.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证权限入口 - 认证失败的情况下访问所有接口都会拦截到此
 *
 * @author wuweixin
 */
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(AdminAuthenticationEntryPoint.class);

    /**
     * 未登录 或 token过期、token验证失败都会触发这个方法
     *
     * @param request
     * @param response
     * @param e
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) {
        WebResponse<Object> objectResult = ResultUtil.genExceptionResult(new BusinessException(BaseResponseEnum.UNAUTHORIZED));
        logger.error("AdminAuthenticationEntryPoint响应 {}", objectResult);
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        ResponseUtils.out(response, objectResult);
    }

}
