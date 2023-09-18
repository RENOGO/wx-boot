package com.wx.common.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuweixin
 * @Date 2023/9/18 16:03
 * @Version 1.0
 */
public abstract class BaseListChain<T extends BaseListChainHandler<R>, R> {

    private final List<BaseListChainHandler<R>> chain = new ArrayList<>();


    public void addHandler(BaseListChainHandler<R> handler) {
        this.chain.add(handler);
    }

    public R handle(R p) {
        R result = p;
        for (BaseListChainHandler<R> rBaseListChainHandler : chain) {
            result = rBaseListChainHandler.handle(result);
        }
        return result;
    }

}
