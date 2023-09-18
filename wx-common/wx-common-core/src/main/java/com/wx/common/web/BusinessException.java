package com.wx.common.web;

/**
 * 业务异常
 * 业务处理时，出现异常，可以抛出该异常
 *
 */
public class BusinessException extends  BaseException {

	private static final long serialVersionUID = 1L;

	public BusinessException(IResponseEnum responseEnum, String message) {
		super(responseEnum, message);
	}

	public BusinessException(IResponseEnum responseEnum) {
		super(responseEnum);
	}

	public BusinessException(IResponseEnum responseEnum, String message, Throwable cause) {
		super(responseEnum, message, cause);
	}

}
