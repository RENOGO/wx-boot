package com.wx.common.base;

/**
 * @Author wuweixin
 * @Date 2023/9/18 16:03
 * @Version 1.0
 */
public abstract class BaseListChainHandler<R> {


    /**
     * @param p
     * @return
     */
    public abstract R handle(R p);
}
