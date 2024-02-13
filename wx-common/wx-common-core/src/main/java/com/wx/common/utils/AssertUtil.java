package com.wx.common.utils;

import cn.hutool.core.util.StrUtil;
import com.wx.common.exception.BusinessException;
import com.wx.common.web.IResponseEnum;
import com.wx.common.web.BaseResponseEnum;

import java.util.function.Supplier;

/**
 * @Author wuweixin
 * @Date 2023/9/26 18:26
 * @Version 1.0
 */
public class AssertUtil {


    /**
     * 操作失败
     *
     * @param b true会抛出异常
     */
    public static void operateFailed(boolean b) {
        operateFailed(b, null);
    }

    /**
     * 操作失败
     *
     * @param b true会抛出异常
     * @param message
     */
    public static void operateFailed(boolean b, String message) {
        xAssert(b, BaseResponseEnum.OPERATE_FAIL, message);
    }


    /**
     * 不合法参数请调用这个
     *
     * @param b true会抛出异常
     */
    public static void parameterIllegal(boolean b) {
        parameterIllegal(b, null);
    }


    /**
     * 不合法参数请调用这个
     *
     * @param b true会抛出异常
     * @param message 自定义响应信息
     */
    public static void parameterIllegal(boolean b, String message) {
        xAssert(b, BaseResponseEnum.PARAMETERS_ILLEGAL, message);
    }


    /**
     * 缺少参数请调用这个
     *
     * @param b true会抛出异常
     */
    public static void missingParameters(boolean b) {
        missingParameters(b, null);
    }


    /**
     * 缺少参数请调用这个
     *
     * @param b true会抛出异常
     * @param message 自定义响应信息
     */
    public static void missingParameters(boolean b, String message) {
        xAssert(b, BaseResponseEnum.MISSING_PARAMETERS, message);
    }

    /**
     * 通用抛异常的方法
     *
     * @param b true会抛出异常
     * @param iResponseEnum 这里主要是为了得到状态码
     */
    public static void xAssert(boolean b, IResponseEnum iResponseEnum) {
        xAssert(b, iResponseEnum, null);
    }

    /**
     * 通用抛异常的方法
     *
     * @param b true会抛出异常
     * @param iResponseEnum 这里主要是为了得到状态码
     * @param message       自定义响应消息，null则取上面枚举的message
     */
    public static void xAssert(boolean b, IResponseEnum iResponseEnum, String message) {
        if (StrUtil.isEmpty(message)) {
            message = iResponseEnum.getMessage();
        }
        String finalMessage = message;
        try {
            isFalse(b, (Supplier<Throwable>) () -> new BusinessException(iResponseEnum, finalMessage));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <X extends Throwable> void isFalse(boolean expression, Supplier<X> errorSupplier) throws X {
        if (expression) {
            throw errorSupplier.get();
        }
    }

}
