package com.wx.auth.model.vo;

import com.wx.common.base.IConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/26 15:33
 * @Version 1.0
 */
@Data
public class LoginResVO implements Serializable, IConvert {

    private String accessToken;

    private String refreshToken;

    private Date expiresTime;

    private String userId;

}
