package com.wx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2023/9/21 19:20
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    /**
     *
     */
    ENABLE(0),
    DISABLE(1);

    private final Integer status;

}
