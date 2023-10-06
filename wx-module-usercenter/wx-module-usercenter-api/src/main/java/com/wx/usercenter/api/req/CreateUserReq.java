package com.wx.usercenter.api.req;

import com.wx.usercenter.api.base.BaseUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wuweixin
 * @Date 2023/9/26 15:41
 * @Version 1.0
 */
@Data
public class CreateUserReq extends BaseUser {

    /**
     * 密码，这里采用sha1进行转换
     */
    @ApiModelProperty(value = "密码，这里采用sha1进行转换")
    @NotBlank(message = "缺少密码")
    private String password;

}
