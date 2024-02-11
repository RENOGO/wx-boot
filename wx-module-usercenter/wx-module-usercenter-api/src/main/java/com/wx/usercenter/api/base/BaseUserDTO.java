package com.wx.usercenter.api.base;

import com.wx.common.base.BaseDTO;
import com.wx.common.validation.annotation.Mobile;
import com.wx.usercenter.api.enums.SexEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2023/9/26 15:49
 * @Version 1.0
 */
@Data
public class BaseUserDTO extends BaseDTO implements Serializable {

    //后续补充权限与角色

    /**
     * 用户名，同时也是账户名
     */
    @ApiModelProperty(value = "用户名，同时也是账户名", required = true)
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,30}$", message = "只能由数字或字母组成")
    @Size(min = 6, max = 30, message = "账户长度只能为6~30个字符")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", required = false)
    @Size(max = 15, message = "用户昵称长度不能超过15各字符")
    private String nickname;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", required = true)
    @Mobile
    private String mobile;
    /**
     * 用户性别
     * <p>
     * {@link SexEnum}
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

}
