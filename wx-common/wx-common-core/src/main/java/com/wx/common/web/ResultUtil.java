package com.wx.common.web;


import com.wx.common.exception.BaseException;

public class ResultUtil {

	public static <T> WebResponse<T> genSuccessResult() {
		return new WebResponse<T>()
				.setCode(BaseResponseEnum.SUCCESS);
	}

	public static <T> WebResponse<T> genSuccessResult(T data) {
		return new WebResponse<T>()
				.setCode(BaseResponseEnum.SUCCESS)
				.setData(data);
	}

	public static <T> WebResponse<T> genFailResult(String message) {
		return new WebResponse<T>()
				.setCode(BaseResponseEnum.BAD_REQUEST)
				.setMessage(message);
	}


	public static <T> WebResponse<T> genFailResult(IResponseEnum responseEnum) {
		return new WebResponse<T>()
				.setCode(responseEnum)
				.setMessage(responseEnum.getMessage());
	}


	public static <T> WebResponse<T> genFailResult(IResponseEnum responseEnum, String message) {
		return new WebResponse<T>()
				.setCode(responseEnum)
				.setMessage(message);
	}

	/**
	 *
	 * @param exception
	 * @return
	 * @param <T>
	 */
	public static <T> WebResponse<T> genExceptionResult(BaseException exception) {
		return new WebResponse<T>()
				.setCode(exception);
	}

	/**
	 * 系统级别错误信息
	 *
	 * @param sysMessage
	 * @param <T>
	 * @return
	 */
	public static <T> WebResponse<T> genSysFailResult(String sysMessage) {
		return new WebResponse<T>()
				.setCode(BaseResponseEnum.BAD_REQUEST)
				.setSysMessage(sysMessage);
	}
}
