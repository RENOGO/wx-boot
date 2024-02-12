package com.wx.usercenter.model.dos;

import com.wx.common.mybatis.base.BaseDO;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2024-02-11 23:45:56
 */
public class SysRoleDO extends BaseDO<SysRoleDO> {
    private static final long serialVersionUID = 809748625663870950L;

    /**
     * 角色key
     */
    private String roleKey;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 状态：0正常，1停用
     */
    private Integer status;
    /**
     * 角色排序
     */
    private Integer num;

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}

