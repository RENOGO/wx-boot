package com.wx.auth.api.dto;

import com.wx.auth.api.enums.AppTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2023/10/6 17:56
 * @Version 1.0
 */
@Data
public class CreateAppDTO implements Serializable {

    private String appName;

    private String remark;

    /**
     * {@link AppTypeEnum}
     */
    private Integer appType;

    /**
     * token过期时间,单位毫秒
     */
    private Long tokenExpired;

}
