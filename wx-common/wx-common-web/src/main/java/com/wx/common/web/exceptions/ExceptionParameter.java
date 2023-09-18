package com.wx.common.web.exceptions;

import com.wx.common.web.WebResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wuweixin
 * @Date 2023/9/18 14:12
 * @Version 1.0
 */
public class ExceptionParameter {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Exception exception;

    private WebResponse<?> result;

    public WebResponse<?> getResult() {
        return result;
    }

    public void setResult(WebResponse<?> result) {
        this.result = result;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
