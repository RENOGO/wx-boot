package com.wx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.common.enums.CommonStatusEnum;
import com.wx.common.mybatis.base.BaseDataEntity;
import com.wx.usercenter.api.enums.SexEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author wuweixin
 * @Date 2023/9/21 17:13
 * @Version 1.0
 */
@TableName("sys_user")
@Data
public class User extends BaseDataEntity<User> {

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
     *
     *  {@link SexEnum}
     */
    private Integer sex;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 帐号状态
     *
     *  {@link CommonStatusEnum}
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
