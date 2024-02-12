package com.wx.usercenter.api.dto;

import com.wx.common.base.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2024/2/11 23:54
 * @Version 1.0
 * @Descritube
 */
@Data
public class SysRoleDTO  extends BaseDTO implements Serializable {


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

}
