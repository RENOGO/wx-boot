package com.wx.auth.service.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.wx.auth.api.dto.TokenDTO;
import com.wx.auth.constants.AuthConstants;
import com.wx.auth.model.dos.AuthAppDO;
import com.wx.auth.service.app.AuthAppService;
import com.wx.auth.utils.ServletUtils;
import com.wx.common.redis.util.RedisUtil;
import com.wx.common.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author wuweixin
 * @Date 2023/10/5 17:46
 * @Version 1.0
 */
@Service
public class TokenService {

    @Autowired
    private AuthAppService appService;

    /**
     * 多缓存结合，进一步提高速度
     */
    private final Map<String, String> JWT_KEY_CACHE = new HashMap<>();

    /**
     * 减少同一线程下重复解析jwt带来的消耗
     */
    private static final ThreadLocal<JWT> JWT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 生成token
     *
     * @param payload  负载
     * @param userinfo
     * @return
     */
    public TokenDTO generalToken(String userId,  Map<String, Object> payload, Map<String, Object> userinfo) {
        return generalToken(userId, getAppIdFromHeader(), payload, userinfo);
    }

    /**
     * 生成token
     *
     * @param payload  负载
     * @param userinfo
     * @return
     */
    public TokenDTO generalToken(String userId, String appId, Map<String, Object> payload, Map<String, Object> userinfo) {
        AuthAppDO app = appService.getAppByAppId(appId);
        AssertUtil.operateFailed(app == null, "应用不存在");
        payload.put(AuthConstants.USER_ID, userId);
        payload.put(AuthConstants.APP_ID, appId);
        String key = app.getJwtKey();
        //存入内存，不需要每次重复取出jwt的key
        JWT_KEY_CACHE.put(appId, key);
        String token = JWTUtil.createToken(payload, key.getBytes());
        RedisUtil.set(getRedisKey(appId, userId, token), userinfo, app.getTokenExpired(), TimeUnit.MILLISECONDS);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(token);
        tokenDTO.setRefreshToken("");
        tokenDTO.setExpiresTime(new Date(System.currentTimeMillis() + app.getTokenExpired()));
        return tokenDTO;
    }


    /**
     * 获得用户id
     *
     * @return
     */
    public String getUserId() {
        return getUserId(getTokenFromHeader());
    }

    /**
     * 获得用户id
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {
        JWT jwt = parseToken(token);
        return jwt.getPayloads().getStr(AuthConstants.USER_ID);
    }

    /**
     * 解析token
     *
     * @return
     */
    public JWT parseToken() {
        return parseToken(getTokenFromHeader());
    }

    /**
     * @param token
     * @return
     */
    public JWT parseToken(String token) {
        return Optional.ofNullable(JWT_THREAD_LOCAL.get()).orElseGet(new Supplier<JWT>() {
            @Override
            public JWT get() {
                JWT jwt = JWTUtil.parseToken(splitToken(token));
                JWT_THREAD_LOCAL.set(jwt);
                return jwt;
            }
        });
    }


    /**
     * 验证token
     *
     * @return
     */
    public boolean verifyToken() {
        return verifyToken(getAppIdFromHeader(), getTokenFromHeader());
    }

    /**
     * 验证token
     * 有些开发者只校验redis有没有这个token，不校验jwt，也是可以的，但理论上jwt和redis都验证一遍才是最安全的，防止有意外插入数据到redis中被当成存在的token而进入业务服务
     * 但这也导致这里的实现变复杂了，这也是我纠结是否需要这样做的一个点
     *
     * @param token
     */
    public boolean verifyToken(String appId, String token) {
        String key = Optional.ofNullable(JWT_KEY_CACHE.get(appId)).orElseGet(new Supplier<String>() {
            @Override
            public String get() {
                AuthAppDO app = appService.getAppByAppId(appId);
                AssertUtil.operateFailed(app == null, "非法应用id");
                JWT_KEY_CACHE.put(appId, app.getJwtKey());
                return app.getJwtKey();
            }
        });
        token = splitToken(token);
        boolean verify = JWTUtil.verify(token, key.getBytes());
        if (verify) {
            verify = RedisUtil.get(getRedisKey(appId, getUserId(), token)) != null;
        }
        return verify;
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getTokenFromHeader() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            throw new RuntimeException("获取HttpServletRequest失败");
        }
        return request.getHeader(AuthConstants.HEADER_AUTH);
    }


    /**
     * 获取appId
     *
     * @return
     */
    public String getAppIdFromHeader() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            throw new RuntimeException("获取HttpServletRequest失败");
        }
        return request.getHeader(AuthConstants.HEADER_APP_ID);
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

    private String getRedisKey(String appId, String userId, String token) {
        return AuthConstants.AUTH + appId + ":" + userId + ":" + token;
    }

}
