package com.wx.common.auth.utils;

/**
 * @Author wuweixin
 * @Date 2023/10/6 15:33
 * @Version 1.0
 */
public class LoginUserUtil {

//
//    private static LoginUserUtil instance = null;
//
//    @PostConstruct
//    public void init() {
//        instance = this;
//    }
//
//
//    public static String getAppId() {
//        HttpServletRequest request = ServletUtils.getRequest();
//        if (request == null) {
//            throw new RuntimeException("获取HttpServletRequest失败");
//        }
//        return request.getHeader(AuthConstants.HEADER_APP_ID);
//    }
//
//    /**
//     * 获取token
//     *
//     * @return
//     */
//    public static String getToken() {
//        HttpServletRequest request = ServletUtils.getRequest();
//        if (request == null) {
//            throw new RuntimeException("获取HttpServletRequest失败");
//        }
//        return request.getHeader(AuthConstants.HEADER_AUTH);
//    }
//
//
//    /**
//     * 获得用户信息
//     *
//     * @return
//     */
//    public static Map<String, Object> getLoginUserInfo() {
//        return getLoginUserInfo(getToken());
//    }
//
//    /**
//     * 获得用户信息
//     *
//     * @param token
//     * @return
//     */
//    public static Map<String, Object> getLoginUserInfo(String token) {
//        JWT jwt = parseToken(token);
//        String userId = jwt.getPayloads().getStr(AuthConstants.USER_ID);
//        return (Map<String, Object>) RedisUtil.get(getRedisKey(userId, token));
//    }
//
//
//
//
//    /**
//     * 生成token
//     *
//     * @param payload  负载
//     * @param userinfo
//     * @return
//     */
//    public static TokenDTO generalToken(String userId, Map<String, Object> payload, Map<String, Object> userinfo) {
//        payload.put(AuthConstants.USER_ID, userId);
//        String token = JWTUtil.createToken(payload, authProperties.getKey().getBytes());
//        RedisUtil.set(getRedisKey(userId, token), userinfo, authProperties.getExpired(), TimeUnit.MILLISECONDS);
//        TokenDTO tokenDTO = new TokenDTO();
//        tokenDTO.setAccessToken(token);
//        tokenDTO.setRefreshToken("");
//        tokenDTO.setExpiresTime(new Date(System.currentTimeMillis() + authProperties.getExpired()));
//        return tokenDTO;
//    }
//
//
//    /**
//     * 解析token
//     *
//     * @return
//     */
//    public static JWT parseToken() {
//        return parseToken(getToken());
//    }
//
//    /**
//     * @param token
//     * @return
//     */
//    public static JWT parseToken(String token,String key) {
//        token = splitToken(token);
//        JWT jwt = JWTUtil.parseToken(token);
//        return jwt;
//    }
//
//
//    /**
//     * 验证token
//     *
//     * @return
//     */
//    public static boolean verifyToken() {
//        return verifyToken(getToken());
//    }
//
//    /**
//     * 验证token
//     *
//     * @param token
//     */
//    public static boolean verifyToken(String token) {
//        token = splitToken(token);
//        boolean verify = JWTUtil.verify(token, authProperties.getKey().getBytes());
//        if (verify) {
//            verify = RedisUtil.get(AuthConstants.AUTH + authProperties.getModuleName()) != null;
//        }
//        return verify;
//    }
//
//
//
//    private static String splitToken(String token) {
//        if (StrUtil.isEmpty(token)) {
//            throw new RuntimeException("token is empty");
//        }
//        if (token.contains(AuthConstants.HEADER_BEARER)) {
//            token = token.replace(AuthConstants.HEADER_BEARER, "");
//        }
//        return token;
//    }
//
//    public static String getRedisKey(String userId, String token) {
//        return AuthConstants.AUTH + authProperties.getModuleName() + ":" + userId + ":" + token;
//    }
}
