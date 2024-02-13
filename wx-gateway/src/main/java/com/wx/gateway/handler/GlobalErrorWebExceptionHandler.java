package com.wx.gateway.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.json.JSONUtil;
import com.wx.common.exception.AuthorizationException;
import com.wx.common.web.BaseResponseEnum;
import com.wx.common.web.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Author wuweixin
 * @Date 2024/2/13 21:21
 * @Version 1.0
 * @Descritube
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("网关异常捕获 {}", JSONUtil.toJsonStr(exchange.getRequest()), ex);
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 设置返回值类型为json
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        BaseResponseEnum baseResponseEnum = BaseResponseEnum.OPERATE_FAIL;
        String message = baseResponseEnum.getMessage();
        //非200状态码错误，如404
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        } else if (ex instanceof SaTokenException) {
            //鉴权失败
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            baseResponseEnum = BaseResponseEnum.UNAUTHORIZED;
            if (ex.getCause().getClass() == NotLoginException.class) {
                message = ex.getCause().getMessage();
            } else if (ex.getCause().getClass() == AuthorizationException.class) {
                message = ex.getCause().getMessage();
            } else {
                message = BaseResponseEnum.UNAUTHORIZED.getMessage();
            }
        }

        final BaseResponseEnum finalBaseResponseEnum = baseResponseEnum;
        final String finalMessage = message;
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(JSONUtil.toJsonStr(ResultUtil
                        .genFailResult(finalBaseResponseEnum, finalMessage)).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
