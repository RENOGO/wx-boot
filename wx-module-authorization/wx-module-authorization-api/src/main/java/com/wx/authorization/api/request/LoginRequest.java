package com.wx.authorization.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2024/2/4 11:34
 * @Version 1.0
 * @Descritube
 */
@Data
public class LoginRequest implements Serializable {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "缺少用户名")
    private String username;

    @ApiModelProperty(value = "密码", notes = "sha256摘要转换")
    @NotBlank(message = "缺少密码")
    private String password;

    @ApiModelProperty(value = "验证码", notes = "验证码")
    @NotBlank(message = "缺少验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码id", notes = "验证码id")
    @NotBlank(message = "缺少验证码凭证")
    private String captchaId;

}
