package com.wx.common.security.constants;

/**
 * @author wuweixin
 */
public class SecurityConstant {


    public static final String LOGIN_API = "/login";

    public static final String LOGIN_METHOD = "POST";

    public static final String REDIS_LOGIN_USER_INFO_KEY = "security:login_user_info:";

    public static final String REDIS_TOKEN_USER_INFO_KEY = "security:user_info:";


    public static final long REDIS_TOKEN_EXPIRED = 60 * 60 * 24;

    public static final long REDIS_REFRESH_TOKEN_EXPIRED = 60 * 60 * 24;


    /**
     * 请求头安全凭证
     */
    public static final String SECURITY_HEADER_TOKEN = "Authorization";

    /**
     * 请求头应用ID
     */
    public static final String SECURITY_HEADER_APP_ID = "X-App-Id";


    /**
     * 请求凭证参数安全凭证TOKEN
     */
    public static final String SECURITY_QUERY_TOKEN = "token";

    /**
     * 请求凭证参数应用ID
     */
    public static final String SECURITY_QUERY_APP_ID = "appId";


}
