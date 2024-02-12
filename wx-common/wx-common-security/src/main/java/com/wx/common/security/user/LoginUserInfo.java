package com.wx.common.security.user;

import com.wx.usercenter.api.enums.SexEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wuweixin
 * @Date 2024/2/7 09:54
 * @Version 1.0
 * @Descritube
 */
@Data
public class LoginUserInfo implements Serializable {



    private List<String> roles;


    private String id;

    /**
     * 用户名，同时也是账户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户性别
     * <p>
     * {@link SexEnum}
     */
    private Integer sex;
    /**
     * 用户头像
     */
    private String avatar;

    private String email;

}
