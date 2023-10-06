package com.wx.auth.api.req;

import lombok.Data;

/**
 * @Author wuweixin
 * @Date 2023/10/2 17:22
 * @Version 1.0
 */
@Data
public class LoginRequest {

    private String username;

    private String password;

    private String captchaId;

    private String captcha;


}
