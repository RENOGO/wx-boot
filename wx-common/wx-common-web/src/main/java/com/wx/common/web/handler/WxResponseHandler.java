package com.wx.common.web.handler;


import cn.hutool.json.JSONUtil;
import com.wx.common.constants.HttpResponseHeaders;
import com.wx.common.utils.RequestIdUtil;
import com.wx.common.web.WebResponse;
import com.wx.common.web.ResultUtil;
import com.wx.common.web.annotation.IgnoreResponseBody;
import com.wx.common.web.config.CommonWebProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * 通用响应体
 *
 * @author wx
 */
@RestControllerAdvice
public class WxResponseHandler implements ResponseBodyAdvice<Object> {



    @Autowired
    private CommonWebProperties properties;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (ObjectUtils.isEmpty(properties.getBaseControllerPath())) {
            return false;
        }
        Annotation annotation = methodParameter.getMethod().getAnnotation(IgnoreResponseBody.class);
        if (annotation != null) {
            return false;
        }
        String packName = methodParameter.getDeclaringClass().getPackage().getName();
        for (String pack :
                properties.getBaseControllerPath()) {
            if (pack.length() > packName.length()) {
                continue;
            }
            String antPack = packName.substring(0, pack.length());
            if (antPack.equals(pack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全局异常捕获的数据是不会到这里来的,这里只处理正常返回的页数数据
     *
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.getHeaders().set(HttpResponseHeaders.REQUEST_ID, RequestIdUtil.getRequestId());
        if (o instanceof WebResponse) {
            return o;
        }
        //必须这样，controller返回string的时候会由另一个类处理，这时候会出现类型转换错误
        //所以这里要转为字符串
        if (o instanceof String) {
            serverHttpResponse.getHeaders().set("Content-Type", "application/json");
            return JSONUtil.toJsonStr(ResultUtil.genSuccessResult(o));
        }
        return ResultUtil.genSuccessResult(o);
    }
    
}
