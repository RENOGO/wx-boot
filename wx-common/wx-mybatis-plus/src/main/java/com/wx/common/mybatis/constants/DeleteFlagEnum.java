package com.wx.common.mybatis.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author wuweixin
 */
public enum DeleteFlagEnum implements IEnum<Integer> {

    EXIST(0, "存在"),

    DELETE(1, "删除");

    @JsonValue  //序列化，枚举转json的时候使用哪个作为值
    @EnumValue  //序列化，数据库DeleteFlag的值映射到这个属性，要使用这个注解
    private Integer value;

    private String desc;

    DeleteFlagEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
