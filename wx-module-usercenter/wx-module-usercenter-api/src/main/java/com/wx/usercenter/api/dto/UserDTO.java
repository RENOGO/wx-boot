package com.wx.usercenter.api.dto;

import com.wx.usercenter.api.base.BaseUser;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:03
 * @Version 1.0
 */
@Data
public class UserDTO extends BaseUser {

    /**
     * 密码，这里采用sha1进行转换
     */
    private String password;


}
