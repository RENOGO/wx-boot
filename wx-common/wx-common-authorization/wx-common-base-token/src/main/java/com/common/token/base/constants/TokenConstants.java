package com.common.token.base.constants;

/**
 * @Author wuweixin
 * @Date 2024/2/11 21:38
 * @Version 1.0
 * @Descritube
 */
public class TokenConstants {

    public static final String MODULE_NAME = "token";

    /**
     * 2分钟权限缓存
     */
    public static final long DEFAULT_PERMISSION_EXPIRE = 60 * 2;

    /**
     * @param token
     * @return
     */
    public static String getLoginUserInfoRedisKey(String id, String token) {
        return MODULE_NAME + "." + id + "." + token;
    }

}
