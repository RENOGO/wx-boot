package com.wx.message.constants;

/**
 * @Author wuweixin
 * @Date 2023/10/2 18:20
 * @Version 1.0
 */
public class RedisConstants {

    public static final String PRE = "message:";

    public static final String CAPTCHA_CODE = PRE + "captcha:";

    public static String getCaptchaCode(String flag, String id) {
        return RedisConstants.CAPTCHA_CODE + flag + "_" + id;
    }


}
