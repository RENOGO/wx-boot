package com.wx.common.auth.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/10/5 17:50
 * @Version 1.0
 */
@Data
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    private Date expiresTime;
}
