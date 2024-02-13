package com.wx.common.web.exceptions.handler;

import com.wx.common.exception.BaseException;
import com.wx.common.exception.BusinessException;
import com.wx.common.web.ResultUtil;
import com.wx.common.web.annotation.ExceptionOrder;
import com.wx.common.web.exceptions.ExceptionParameter;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wuweixin
 * @Date 2023/9/18 14:30
 * @Version 1.0
 */
@Component
@ExceptionOrder(order = 9998)
public class DubboExceptionHandler extends BaseWebResponseExceptionHandler {

    private final Pattern pattern = Pattern.compile("code=(\\d+),\\s+message=(\\S+)]");
    private static final String EXCEPTION_RPC = "org.apache.dubbo.rpc.RpcException";

    @Override
    public ExceptionParameter handle(ExceptionParameter exceptionParameter) {
        //开发者有自定义的类处理过，这里就不再处理了，直接返回
        if (exceptionParameter.getResult() != null) {
            return exceptionParameter;
        }
        Exception exception = exceptionParameter.getException();
//        if (exception.toString().startsWith(EXCEPTION_RPC)) {
//            return
//        }
        String message = exception.getMessage();
        if (message.contains(BusinessException.class.getName()) || message.contains(BaseException.class.getName())) {
            // 使用正则表达式匹配code和message的值
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String code = matcher.group(1);
                String msg = matcher.group(2);
                exceptionParameter.setResult(ResultUtil.genExceptionResult(new BaseException(Integer.parseInt(code), msg)));
                return exceptionParameter;
            }
        }
        return exceptionParameter;
    }
}
