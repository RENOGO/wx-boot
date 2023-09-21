package com.wx.usercenter.api.dto;

import com.wx.common.base.BaseDataDTO;
import com.wx.common.enums.CommonStatusEnum;
import com.wx.usercenter.api.enums.SexEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:03
 * @Version 1.0
 */
@Data
public class UserDTO extends BaseDataDTO {

    /**
     * 用户名，同时也是账户名
     */
    private String username;
    /**
     * 密码，这里采用sha1进行转换
     */
    private String password;
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
    /**
     * 帐号状态
     * <p>
     * {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

}
