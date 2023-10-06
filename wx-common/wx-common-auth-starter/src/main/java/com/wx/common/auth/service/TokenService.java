package com.wx.common.auth.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.wx.common.auth.config.AuthProperties;
import com.wx.common.auth.constants.AuthConstants;
import com.wx.common.auth.dto.TokenDTO;
import com.wx.common.auth.utils.ServletUtils;
import com.wx.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author wuweixin
 * @Date 2023/10/5 17:46
 * @Version 1.0
 */
@Deprecated
@Slf4j
public class TokenService {

    @Autowired
    private AuthProperties authProperties;


    /**
     * 生成token
     *
     * @param payload  负载
     * @param userinfo
     * @return
     */
    public TokenDTO generalToken(String userId, Map<String, Object> payload, Map<String, Object> userinfo) {
        payload.put(AuthConstants.USER_ID, userId);
        String token = JWTUtil.createToken(payload, authProperties.getKey().getBytes());
        RedisUtil.set(getRedisKey(userId, token), userinfo, authProperties.getExpired(), TimeUnit.MILLISECONDS);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(token);
        tokenDTO.setRefreshToken("");
        tokenDTO.setExpiresTime(new Date(System.currentTimeMillis() + authProperties.getExpired()));
        return tokenDTO;
    }


    /**
     * 解析token
     *
     * @return
     */
    public JWT parseToken() {
        return parseToken(getToken());
    }

    /**
     * @param token
     * @return
     */
    public JWT parseToken(String token) {
        token = splitToken(token);
        JWT jwt = JWTUtil.parseToken(token);
        return jwt;
    }


    /**
     * 验证token
     *
     * @return
     */
    public boolean verifyToken() {
        return verifyToken(getToken());
    }

    /**
     * 验证token
     *
     * @param token
     */
    public boolean verifyToken(String token) {
        token = splitToken(token);
        boolean verify = JWTUtil.verify(token, authProperties.getKey().getBytes());
        if (verify) {
            verify = RedisUtil.get(AuthConstants.AUTH + authProperties.getModuleName()) != null;
        }
        return verify;
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            throw new RuntimeException("获取HttpServletRequest失败");
        }
        return request.getHeader(AuthConstants.HEADER_AUTH);
    }

    private String splitToken(String token) {
        if (StrUtil.isEmpty(token)) {
            throw new RuntimeException("token is empty");
        }
        if (token.contains(AuthConstants.HEADER_BEARER)) {
            token = token.replace(AuthConstants.HEADER_BEARER, "");
        }
        return token;
    }

    public String getRedisKey(String userId, String token) {
        return AuthConstants.AUTH + authProperties.getModuleName() + ":" + userId + ":" + token;
    }

}
