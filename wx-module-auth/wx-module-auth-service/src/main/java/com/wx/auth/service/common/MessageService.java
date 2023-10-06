package com.wx.auth.service.common;

import com.wx.auth.api.req.LineCaptchaRequest;
import com.wx.auth.model.vo.CaptchaVO;

/**
 * @Author wuweixin
 * @Date 2023/10/5 11:05
 * @Version 1.0
 */
public interface MessageService {


    /**
     * 获得验证码
     * @param lineCaptchaRequest
     * @return
     */
    CaptchaVO getLineCaptcha(LineCaptchaRequest lineCaptchaRequest);


    /**
     * 校验验证码
     * @param captchaId
     * @param captcha
     * @return
     */
    boolean verifyCaptcha(String captchaId, String captcha);


}
