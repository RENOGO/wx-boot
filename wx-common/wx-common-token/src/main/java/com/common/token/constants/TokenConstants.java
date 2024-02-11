package com.common.token.constants;

/**
 * @Author wuweixin
 * @Date 2024/2/11 21:38
 * @Version 1.0
 * @Descritube
 */
public class TokenConstants {

    public static final String MODULE_NAME = "token";

    /**
     * 当小于这个时间（秒）的时候，如果有触发到getLoginUser方法，则会续期用户信息（与satoken的续期无冲突）
     */
    public static final long DEFAULT_RENEW_USERINFO = 60 * 60 * 24 * 7;

    /**
     * @param token
     * @return
     */
    public static String getLoginUserInfoRedisKey(String id, String token) {
        return MODULE_NAME + "." + id + "." + token;
    }

}
