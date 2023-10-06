package com.wx.auth.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author wuweixin
 * @Date 2023/10/6 17:40
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum AppTypeEnum {

    SYS(1, "系统级别APP，不允许删除和冻结"),
    BIZ(2, "业务系统");

    private final int type;
    private final String describe;

}
