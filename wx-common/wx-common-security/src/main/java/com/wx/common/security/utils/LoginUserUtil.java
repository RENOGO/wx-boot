package com.wx.common.security.utils;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.wx.common.redis.util.RedisUtil;
import com.wx.common.security.config.SecurityProperties;
import com.wx.common.security.constants.SecurityConstant;
import com.wx.common.security.user.LoginUser;
import com.wx.common.security.user.UserJwtPayload;
import com.wx.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author wuweixin
 */
@Component
public class LoginUserUtil {

    @Autowired
    private SecurityProperties securityProperties;

    private static LoginUserUtil loginUserUtil;

    @PostConstruct
    public void init() {
        loginUserUtil = this;
    }


    /**
     * 获得登录的时候拿到的用户信息
     *
     * @return
     */
    public static LoginUser getLoginUserInfo() {
        return parseLoginUserToken();
    }

    /**
     * 获得登录的时候拿到的用户信息
     *
     * @return
     */
    public static LoginUser getLoginUserInfo(String token) {
        return parseLoginUserToken(token);
    }


    /**
     * 获得用户id
     *
     * @return
     */
    public static String getUserId() {
        if (getLoginUserInfo() == null) {
            return null;
        }
        if (getLoginUserInfo().getUserInfo() == null) {
            return null;
        }
        return getLoginUserInfo().getUserInfo().getId();
    }


    /**
     * @return
     */
    public static String getToken() {
        String token = Optional
                .ofNullable(getHeaderValue(SecurityConstant.SECURITY_HEADER_TOKEN))
                .orElse(getQueryValue(SecurityConstant.SECURITY_QUERY_TOKEN));
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        token = token.replace(SecurityConstant.SECURITY_HEADER_TOKEN
                        .replace(" ", "%20"), "")
                .replace(SecurityConstant.SECURITY_HEADER_TOKEN, "");
        return token;
    }

    /**
     * @return
     */
    public static String getAppId() {
        return Optional
                .ofNullable(getHeaderValue(SecurityConstant.SECURITY_HEADER_APP_ID))
                .orElse(getQueryValue(SecurityConstant.SECURITY_QUERY_APP_ID));
    }


    /**
     * 生成token
     *
     * @param userJwtPayload
     * @param key
     * @return
     */
    public static String generalToken(UserJwtPayload userJwtPayload, Date expires, byte[] key) {
        return JWT.create()
                .addPayloads(BeanUtil.beanToMap(userJwtPayload))
                .setKey(key)
                .setExpiresAt(expires)
                .sign();
    }


    //------------------------------


    /**
     * 存入本系统登录的用户信息
     *
     * @param loginUser
     */
    public static void putLoginUserInfo(LoginUser loginUser) {
        RedisUtil.set(getLoginUserInfoRedisKey(loginUser.getToken()), loginUser,
                loginUserUtil.securityProperties.getExpires());
    }

    /**
     *
     */
    public static void delCurrentToken() {
        RedisUtil.del(getLoginUserInfoRedisKey(getToken()));
    }

    /**
     * 解析token
     *
     * @return
     */
    private static LoginUser parseLoginUserToken(String token) {
        return (LoginUser) RedisUtil.get(getLoginUserInfoRedisKey(token));
    }

    /**
     * 解析token
     *
     * @return
     */
    private static LoginUser parseLoginUserToken() {
        return parseLoginUserToken(getToken());
    }

    /**
     * @param token
     * @return
     */
    private static String getLoginUserInfoRedisKey(String token) {
        return SecurityConstant.REDIS_LOGIN_USER_INFO_KEY + token;
    }


    //--------------------------


    private static String getHeaderValue(String key) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return null;
        }
        return ServletUtils.getRequest().getHeader(key);
    }

    private static String getQueryValue(String key) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return null;
        }
        String queryString = request.getQueryString();
        Map<String, String> map = StringUtil.query2Map(queryString);
        if (map == null) {
            return null;
        }
        return map.get(key);
    }


}
