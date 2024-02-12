package com.wx.usercenter.model.dos;


import com.wx.common.mybatis.base.BaseDO;

/**
 * (SysPermission)表实体类
 *
 * @author makejava
 * @since 2024-02-12 20:03:31
 */
@SuppressWarnings("serial")
public class SysPermissionDO extends BaseDO<SysPermissionDO> {

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


    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}

