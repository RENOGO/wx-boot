package com.wx.common.base;


/**
 * @Author wuweixin
 * @Date 2023/9/18 13:55
 * @Version 1.0
 */
public abstract class BaseChain<T extends BaseChainHandler<R>, R> {

    private BaseChainHandler<R> head = null;
    private BaseChainHandler<R> tail = null;

    public void addHandler(BaseChainHandler<R> handler) {
        handler.setNext(null);
        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }
        tail.setNext(handler);
        tail = handler;
    }

    public R handle(R p) {
        return head.handle(p);
    }

}
