package com.wx.common.security.utils;

import cn.hutool.crypto.SecureUtil;

import java.util.Optional;

/**
 * @Author wuweixin
 * @Date 2023/10/5 14:49
 * @Version 1.0
 */
public class PasswordUtil {

    /**
     * 对密码进行摘要计算
     *
     * @param pwd
     * @return
     */
    public static String encode(String pwd, String salt) {
        salt = Optional.ofNullable(salt).orElse("");
        return SecureUtil.sha256(pwd + salt);
    }


    /**
     * @param n
     * @param t
     * @return
     */
    public static boolean matches(String n, String t, String salt) {
        return encode(n, salt).equals(t);
    }


}
