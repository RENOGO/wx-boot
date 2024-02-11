package com.common.token.model;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.wx.usercenter.api.dto.UserDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2024/2/2 09:04
 * @Version 1.0
 */
@Data
public class LoginUser implements Serializable {

    /**
     * token信息
     */
    private SaTokenInfo tokenInfo;

    /**
     * 用户信息
     */
    private UserDTO userInfo;


}
