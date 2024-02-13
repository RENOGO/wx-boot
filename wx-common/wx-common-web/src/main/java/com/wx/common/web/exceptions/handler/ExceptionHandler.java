package com.wx.common.web.exceptions.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.wx.common.web.ResultUtil;
import com.wx.common.web.annotation.ExceptionOrder;
import com.wx.common.web.config.CommonWebProperties;
import com.wx.common.web.constants.CommonWebConstants;
import com.wx.common.web.exceptions.ExceptionParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Author wuweixin
 * @Date 2023/9/18 13:40
 * @Version 1.0
 */
@Component
@ExceptionOrder(order = 9999)
public class ExceptionHandler extends BaseWebResponseExceptionHandler {

    @Autowired
    private CommonWebProperties commonWebProperties;


    @Override
    public ExceptionParameter handle(ExceptionParameter exceptionParameter) {
        //开发者有自定义的类处理过，这里就不再处理了，直接返回
        //这段代码不抽到基类，防止部分开发者有特殊的需求
        if (exceptionParameter.getResult() != null) {
            return exceptionParameter;
        }
        Exception e = exceptionParameter.getException();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argNotValid = (MethodArgumentNotValidException) e;
            String argNotValidMsg = argNotValid.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            exceptionParameter.setResult(ResultUtil.genFailResult(argNotValidMsg));
        }
        if (CommonWebConstants.ACCESS_DENIED_CLASS.equals(e.getClass().getName())) {
            exceptionParameter.setResult(ResultUtil.genFailResult("无权限访问"));
        }
        //参数不合法
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException argNotValid = (MissingServletRequestParameterException) e;
            String argNotValidMsg = "缺少" + argNotValid.getParameterName() + "参数";
            exceptionParameter.setResult(ResultUtil.genFailResult(argNotValidMsg));
        }
        //请求方式错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            exceptionParameter.setResult(ResultUtil.genFailResult("请求方式错误"));
        }
        if (e instanceof IllegalArgumentException) {
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) e;
            exceptionParameter.setResult(ResultUtil.genFailResult(illegalArgumentException.getMessage()));
        }
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations =
                    constraintViolationException.getConstraintViolations();
            exceptionParameter.setResult(ResultUtil.genFailResult(constraintViolations.iterator().next().getMessageTemplate()));
        }
        if (exceptionParameter.getResult() == null) {
            exceptionParameter
                    .setResult(ResultUtil.genSysFailResult(commonWebProperties.isShowSysError() ? ExceptionUtil.getMessage(e) : ""));
        }
        return handle(exceptionParameter);
    }
}
