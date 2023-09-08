package com.wx.common.web.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.wx.common.constants.HttpResponseHeaders;
import com.wx.common.utils.RequestIdUtil;
import com.wx.common.web.BaseException;
import com.wx.common.web.WebResponse;
import com.wx.common.web.WebResponseGenerator;
import com.wx.common.web.config.CommonWebProperties;
import com.wx.common.web.constants.CommonWebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author wwx
 * @date 2021/10/26 16:26
 * @description
 */
@RestControllerAdvice
public class WxExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WxExceptionHandler.class);

    @Autowired
    private CommonWebProperties commonWebProperties;


    /**
     * 系统级别错误，这里可以再进行区分，比如请求方式错误的可以单独抛出这个异常提示
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
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argNotValid = (MethodArgumentNotValidException) e;
            //这里不判断数组越界，因为一定是有问题才会有这个异常的，取第一个即可
            String argNotValidMsg = argNotValid.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return WebResponseGenerator.genFailResult(argNotValidMsg);
        }
        if (CommonWebConstants.ACCESS_DENIED_CLASS.equals(e.getClass().getName())) {
            return WebResponseGenerator.genFailResult("无权限访问");
        }
        //参数不合法
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException argNotValid = (MissingServletRequestParameterException) e;
            String argNotValidMsg = "缺少" + argNotValid.getParameterName() + "参数";
            return WebResponseGenerator.genFailResult(argNotValidMsg);
        }
        //请求方式错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return WebResponseGenerator.genFailResult("请求方式错误");
        }
        if (e instanceof IllegalArgumentException) {
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) e;
            return WebResponseGenerator.genFailResult(illegalArgumentException.getMessage());
        }
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations =
                    constraintViolationException.getConstraintViolations();
            return WebResponseGenerator.genFailResult(constraintViolations.iterator().next().getMessageTemplate());
        }
        return WebResponseGenerator.genSysFailResult(commonWebProperties.isShowSysError() ? ExceptionUtil.getMessage(e) : "");
    }

    /**
     * 业务错误
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
