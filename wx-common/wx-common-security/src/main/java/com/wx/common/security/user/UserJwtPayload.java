package com.wx.common.security.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wuweixin
 * @Date 2024/2/7 10:04
 * @Version 1.0
 * @Descritube
 */
@Data
public class UserJwtPayload implements Serializable {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

//    /**
//     * token类型
//     */
//    private Integer type;

}
