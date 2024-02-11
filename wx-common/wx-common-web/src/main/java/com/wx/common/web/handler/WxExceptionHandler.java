package com.wx.common.web.handler;

import com.wx.common.constants.HttpResponseHeaders;
import com.wx.common.utils.RequestIdUtil;
import com.wx.common.web.BaseException;
import com.wx.common.web.WebResponse;
import com.wx.common.web.WebResponseGenerator;
import com.wx.common.web.exceptions.ExceptionChain;
import com.wx.common.web.exceptions.ExceptionParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wwx
 * @date 2021/10/26 16:26
 * @description
 */
@RestControllerAdvice
public class WxExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WxExceptionHandler.class);


    @Autowired
    private ExceptionChain exceptionChain;

    /**
     * 这里主要处理的是RuntimeException
     * 能确定的异常，建议用BaseException主动抛出
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public WebResponse<?> sysError(HttpServletRequest request, HttpServletResponse response, Exception e) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpResponseHeaders.REQUEST_ID, RequestIdUtil.getRequestId());
        logger.error("request-id: {}", RequestIdUtil.getRequestId(), e);
        ExceptionParameter exceptionParameter = new ExceptionParameter();
        exceptionParameter.setRequest(request);
        exceptionParameter.setResponse(response);
        exceptionParameter.setException(e);
        return exceptionChain.handle(exceptionParameter).getResult();
    }

    /**
     * 业务错误/代码中能确定的异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public WebResponse<?> appError(HttpServletResponse response, BaseException e) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpResponseHeaders.REQUEST_ID, RequestIdUtil.getRequestId());
        return WebResponseGenerator.genExceptionResult(e);
    }


}
