package com.wx.common.exception;

import cn.hutool.core.util.StrUtil;
import com.wx.common.web.IResponseEnum;

/**
 * 异常基类
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	protected int code;

	/**
	 * 错误信息
	 */
	protected String message;


	public BaseException() {
		super();
	}

	public BaseException(IResponseEnum errorInterface) {
		super(errorInterface.getMessage());
		this.code = errorInterface.getCode();
		this.message = errorInterface.getMessage();
	}

	public BaseException(IResponseEnum errorInterface, String message) {
		this(errorInterface, message, null);
	}

	public BaseException(IResponseEnum errorInterface, Throwable cause) {
		super(errorInterface.getMessage(), cause);
		this.code = errorInterface.getCode();
		this.message = errorInterface.getMessage();
	}

	public BaseException(IResponseEnum errorInterface, String message, Throwable cause) {
		super(errorInterface.getMessage(), cause);
		this.code = errorInterface.getCode();
		if (StrUtil.isEmpty(message)) {
			message = errorInterface.getMessage();
		}
		this.message = message;
	}

	public BaseException(String message) {
		super(message);
		this.message = message;
	}

	public BaseException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseException [code=" + code + ", message=" + message + "]";
	}
}
