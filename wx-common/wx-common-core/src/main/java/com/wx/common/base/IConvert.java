package com.wx.common.base;

import cn.hutool.core.bean.BeanUtil;

/**
 * @Author wuweixin
 * @Date 2023/10/5 11:06
 * @Version 1.0
 */
public interface IConvert {

    /**
     * @param source
     */
    default IConvert convert(Object source) {
        BeanUtil.copyProperties(source, this);
        return this;
    }

}
