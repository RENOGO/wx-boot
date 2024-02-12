package com.common.token.base.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.common.token.base.constants.TokenConstants;
import com.common.token.base.model.LoginUser;
import com.common.token.base.model.TokenUserInfo;
import com.wx.common.redis.util.RedisUtil;

import java.util.Set;

/**
 * @Author wuweixin
 * @Date 2024/2/11 21:29
 * @Version 1.0
 * @Descritube  获取用户登录信息的工具
 */
public class LoginUtil {


    /**
     * 登入
     *
     * @param id
     * @param tokenUserInfo
     * @return
     */
    public static LoginUser login(String id, TokenUserInfo tokenUserInfo) {
        StpUtil.login(id);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginUser loginUser = new LoginUser();
        loginUser.setTokenInfo(tokenInfo);
        loginUser.setUserInfo(tokenUserInfo);
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
     * 获得登录的时候缓存下来的用户信息
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
     * @param tokenUserInfo
     */
    public static void refreshLoginUser(TokenUserInfo tokenUserInfo) {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Set<String> keys = RedisUtil.keys(TokenConstants.getLoginUserInfoRedisKey(tokenInfo.getLoginId().toString(), "*"));
        if (CollectionUtil.isEmpty(keys)) {
            return;
        }
        for (String key : keys) {
            LoginUser loginUser = getLoginUser(key);
            loginUser.setUserInfo(tokenUserInfo);
            putLoginUser(key, loginUser, loginUser.getTokenInfo().getTokenTimeout());
        }
    }

    /**
     * 获取用户id
     * @return
     */
    public static String getUserId() {
        return (String) StpUtil.getLoginId();
    }

    /**
     * 获取用户token
     * @return
     */
    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * @param key
     * @return
     */
    private static LoginUser getLoginUser(String key) {
        return (LoginUser) RedisUtil.get(key);
    }

    /**
     *
     * @param key
     * @param loginUser
     * @param expireSeconds
     */
    private static void putLoginUser(String key, LoginUser loginUser, long expireSeconds) {
        RedisUtil.set(key, loginUser, expireSeconds);
    }


}
