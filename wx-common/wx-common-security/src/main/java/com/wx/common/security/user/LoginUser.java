package com.wx.common.security.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2024/2/2 09:04
 * @Version 1.0
 */
@Data
public class LoginUser implements Serializable {

    /**
     * 凭证token
     */
    private String token;
    /**
     * 刷新的token
     */
    private String refreshToken;
    /**
     * token过期时间
     */
    private Date tokenExpiredDate;
    /**
     *  刷新token过期的时间
     */
    private Date refreshTokenExpiredDate;

    /**
     *  应用id
     */
    private String appId;

    /**
     *  用户信息
     */
    private LoginUserInfo userInfo;


}
