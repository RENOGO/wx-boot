package com.common.token.base.constants;

/**
 * @Author wuweixin
 * @Date 2024/2/12 22:25
 * @Version 1.0
 * @Descritube
 */
public class TokenRedisConstants {

    public static final String USER_PERMISSION = TokenConstants.MODULE_NAME + ".permission";

    public static final String USER_ROLE = TokenConstants.MODULE_NAME + ".role";


    /**
     * 查询用户权限key
     * @param userId
     * @return
     */
    public static String getUserPermission(String userId) {
        return USER_PERMISSION + "." + userId;
    }

    /**
     * 查询用户角色key
     * @param userId
     * @return
     */
    public static String getUserRole(String userId) {
        return USER_ROLE + "." + userId;
    }
}
