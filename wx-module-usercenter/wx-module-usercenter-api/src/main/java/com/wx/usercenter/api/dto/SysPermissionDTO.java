package com.wx.usercenter.api.dto;

import com.wx.common.base.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2024/2/12 21:05
 * @Version 1.0
 * @Descritube
 */
@Data
public class SysPermissionDTO extends BaseDTO implements Serializable {

    /**
     * 权限key
     */
    private String permission;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * api地址
     */
    private String api;
    /**
     * 状态0正常1停用
     */
    private Integer status;

}
