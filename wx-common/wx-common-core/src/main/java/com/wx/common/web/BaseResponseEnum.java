package com.wx.common.web;

import cn.hutool.http.HttpStatus;

/**
 * 响应枚举类
 * 基础操作错误定义
 * 该枚举类占用 2xx、4xx、5xx、4xxx、4xxxx五个数字段，业务系统不建议占用
 */
public enum BaseResponseEnum implements IResponseEnum {

    /**
     * 系统自动抛出的业务状态码，一般不需要手动抛出
     */
    SUCCESS(HttpStatus.HTTP_OK, "success"),
    BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST, "错误请求!"),
    UNAUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "鉴权失败!"),
    NOT_FOUND(HttpStatus.HTTP_NOT_FOUND, "未找到该资源!"),
    INTERNAL_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "内部错误!"),
    UNAVAILABLE(HttpStatus.HTTP_UNAVAILABLE, "服务器正忙，请稍后再试!"),
    /**
     * 业务系统的通用错误状态码，可根据情况自行抛出
     * {@link com.wx.common.utils.AssertUtil} 这个工具类封装了相关的异常抛出
     */
    OPERATE_FAIL(4000, "操作失败"),
    MISSING_PARAMETERS(4001, "缺少参数"),
    PARAMETERS_ILLEGAL(4002, "参数不合法");


    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;


    BaseResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
