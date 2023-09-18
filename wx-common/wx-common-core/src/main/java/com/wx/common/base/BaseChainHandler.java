package com.wx.common.base;

/**
 * @Author wuweixin
 * @Date 2023/9/18 13:55
 * @Version 1.0
 * T-BaseChainHandler的子类
 * R-handle的处理和响应的类
 */
public abstract class BaseChainHandler<R> {

    protected BaseChainHandler<R> next;

    public void setNext(BaseChainHandler<R> next) {
        this.next = next;
    }


    /**
     * @param p
     * @return
     */
    public abstract R handle(R p);


}
