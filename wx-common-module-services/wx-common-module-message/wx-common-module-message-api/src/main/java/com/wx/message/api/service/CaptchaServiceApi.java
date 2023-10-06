package com.wx.message.api.service;

import com.wx.message.api.dto.CaptchaDTO;

/**
 * @Author wuweixin
 * @Date 2023/10/2 17:51
 * @Version 1.0
 */
public interface CaptchaServiceApi {

    /**
     * 生成图形验证码
     *
     * @param flag
     * @return
     */
    CaptchaDTO generateLineCaptcha(String flag, int width, int height);


    /***
     * 验证验证码是否正确
     * @param flag
     * @param captchaId
     * @param captcha
     * @param ignoreCase 是否忽略大小写
     * @return
     */
    boolean verifyCaptcha(String flag, String captchaId, String captcha, boolean ignoreCase);

}
