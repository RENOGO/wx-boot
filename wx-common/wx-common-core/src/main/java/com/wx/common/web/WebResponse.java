package com.wx.common.web;

import cn.hutool.json.JSONUtil;
import com.wx.common.exception.BaseException;

import java.io.Serializable;

/**
 * @Author WX
 * @Date 2023/1/2 12:38
 * @Version 1.0
 */
public class WebResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;
    private String sysMessage;

    public WebResponse() {
    }

    public WebResponse<T> setCode(IResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        return this;
    }

    public WebResponse<T> setCode(BaseException exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public WebResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public WebResponse<T> setSysMessage(String sysMessage) {
        this.sysMessage = sysMessage;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public String getSysMessage() {
        return this.sysMessage;
    }

    public WebResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
