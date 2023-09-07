package com.wx.common.mybatis.base;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wwx
 * @date 2021/7/22 11:16
 */
public class BaseController<T extends IBaseService<?>> {

    @Autowired
    private T service;

    public T getService() {
        return service;
    }

}
