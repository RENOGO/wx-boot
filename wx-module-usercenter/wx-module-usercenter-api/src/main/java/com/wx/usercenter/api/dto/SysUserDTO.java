package com.wx.usercenter.api.dto;

import com.wx.usercenter.api.base.BaseUserDTO;
import lombok.Data;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:03
 * @Version 1.0
 */
@Data
public class SysUserDTO extends BaseUserDTO {

    /**
     * 密码，这里采用sha1进行转换
     */
    private String password;


}
