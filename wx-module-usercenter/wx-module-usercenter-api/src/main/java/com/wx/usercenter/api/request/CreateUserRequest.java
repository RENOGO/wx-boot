package com.wx.usercenter.api.request;

import com.wx.usercenter.api.base.BaseUserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wuweixin
 * @Date 2023/9/26 15:41
 * @Version 1.0
 */
@Data
public class CreateUserRequest extends BaseUserDTO {

    /**
     * 密码，这里采用sha1进行转换
     */
    @ApiModelProperty(value = "密码，这里采用sha1进行转换")
    @NotBlank(message = "缺少密码")
    private String password;

}
