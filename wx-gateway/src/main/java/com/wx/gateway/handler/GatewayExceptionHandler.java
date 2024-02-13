package com.wx.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;


/**
 * @author wwx
 * @date 2021/9/18 14:42
 * @description 网关异常响应
 */
@Slf4j
public class GatewayExceptionHandler extends DefaultErrorWebExceptionHandler {


    public GatewayExceptionHandler(ErrorAttributes errorAttributes,
                                   WebProperties.Resources resources,
                                   ErrorProperties errorProperties,
                                   ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    /**
     * 这个异常拦截是不区分业务错误还是系统错误的，所以我们只能从异常类型来区分
     *
     * @param request
     * @param options
     * @return
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        return null;
    }


    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderCustomErrorResponse);
    }

    protected Mono<ServerResponse> renderCustomErrorResponse(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        return ServerResponse.status(getHttpStatus(error))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromValue(error));
    }

    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        if (errorAttributes == null) {
            return 404;
        }
        int statusCode = (int) errorAttributes.get("code");
        if (statusCode == 401) {
            return statusCode;
        }
        Integer httpStatusErrorCode = (Integer) errorAttributes.get("httpStatusErrorCode");
        //http状态码不是200的话，就在这里抛出
        if (httpStatusErrorCode != null) {
            return httpStatusErrorCode;
        }
        return 200;
    }


}
