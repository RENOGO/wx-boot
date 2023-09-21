package com.wx.usercenter.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2023/9/21 17:21
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum SexEnum {


    /**
     *
     */
    MALE(1),
    FEMALE(2),
    UNKNOWN(3);

    /**
     * 性别
     */
    private final Integer sex;

}
