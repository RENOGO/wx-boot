package com.wx.message.api.dto;

import com.wx.common.base.IConvert;
import lombok.Data;

/**
 * @Author wuweixin
 * @Date 2023/10/2 17:58
 * @Version 1.0
 */
@Data
public class CaptchaDTO implements IConvert {

    /**
     * 图像验证码id
     */
    private String captchaId;

    /**
     * 图形验证码的base64
     */
    private String base64;

}
