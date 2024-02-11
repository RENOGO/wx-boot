package com.common.token.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.common.token.constants.TokenConstants;
import com.common.token.model.LoginUser;
import com.wx.common.redis.util.RedisUtil;
import com.wx.usercenter.api.dto.UserDTO;

import java.util.Set;

/**
 * @Author wuweixin
 * @Date 2024/2/11 21:29
 * @Version 1.0
 * @Descritube
 */
public class LoginUtil {



    /**
     * 登入
     *
     * @param id
     * @param userDTO
     * @return
     */
    public static LoginUser login(String id, UserDTO userDTO) {
        StpUtil.login(id);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginUser loginUser = new LoginUser();
        loginUser.setTokenInfo(tokenInfo);
        loginUser.setUserInfo(userDTO);
        putLoginUser(TokenConstants.getLoginUserInfoRedisKey(id, tokenInfo.tokenValue), loginUser, tokenInfo.getTokenTimeout());
        return loginUser;
    }

    /**
     * 登出
     *
     * @param id
     * @param token
     */
    public static void logout(String id, String token) {
        StpUtil.logoutByTokenValue(token);
        RedisUtil.del(TokenConstants.getLoginUserInfoRedisKey(id, token));
    }


    /**
     * 获得登录的用户信息
     *
     * @return
     */
    public static LoginUser getLoginUser() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return getLoginUser(TokenConstants.getLoginUserInfoRedisKey(tokenInfo.loginId.toString(), tokenInfo.tokenValue));
    }

    /**
     * 刷新用户信息
     *
     * @param userInfoDTO
     */
    public static void refreshLoginUser(UserDTO userInfoDTO) {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Set<String> keys = RedisUtil.keys(TokenConstants.getLoginUserInfoRedisKey(tokenInfo.getLoginId().toString(), "*"));
        if (CollectionUtil.isEmpty(keys)) {
            return;
        }
        for (String key : keys) {
            LoginUser loginUser = getLoginUser(key);
            loginUser.setUserInfo(userInfoDTO);
            putLoginUser(key, loginUser, loginUser.getTokenInfo().getTokenTimeout());
        }
    }

    /**
     * @param key
     * @return
     */
    private static LoginUser getLoginUser(String key) {
        return (LoginUser) RedisUtil.get(key);
    }

    private static void putLoginUser(String key, LoginUser loginUser, long expireSeconds) {
        RedisUtil.set(key, loginUser, expireSeconds);
    }


}
