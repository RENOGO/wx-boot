package com.wx.common.web;

import cn.hutool.http.HttpStatus;

/**
 * 响应枚举类
 * 基础操作错误定义
 * 该枚举类占用 2xx、4xx、5xx、4xxx、4xxxx五个数字段，业务系统不建议占用
 *
 */
public enum ResponseEnum implements IResponseEnum {

	SUCCESS(HttpStatus.HTTP_OK, "success"),
	BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST, "错误请求!"),
	UNAUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "鉴权失败!"),
	NOT_FOUND(HttpStatus.HTTP_NOT_FOUND, "未找到该资源!"),
	INTERNAL_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "内部错误!"),
	UNAVAILABLE(HttpStatus.HTTP_UNAVAILABLE, "服务器正忙，请稍后再试!"),
	/**
	 * 没有具体归类的通用错误状态码
	 */
	OPERATE_FAIL(4000, "操作失败"),
	MISSING_PARAMETERS(4001, "缺少参数"),
	PARAMETERS_ILLEGAL(4002, "参数不合法"),
	LOGIN_FAIL(401001, "登录失败"),
	ACCOUNT_OR_PWD_ILLEGAL(401002, "账户或密码错误");


	/**
	 * 返回码
	 */
	private int code;
	/**
	 * 返回消息
	 */
	private String message;


	ResponseEnum(int code, String message) {
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
