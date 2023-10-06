package com.wx.message.api;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.wx.common.redis.util.RedisUtil;
import com.wx.message.api.dto.CaptchaDTO;
import com.wx.message.api.service.CaptchaServiceApi;
import com.wx.message.constants.RedisConstants;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Locale;
import java.util.UUID;

/**
 * @Author wuweixin
 * @Date 2023/10/2 18:08
 * @Version 1.0
 */
@DubboService
public class CaptchaServiceApiImpl implements CaptchaServiceApi {

    /**
     * 图形验证码模块不对外开放http接口，防止出现恶意调用后，无法溯源是在哪个系统作为入口入侵的
     *
     * @param flag
     * @return
     */
    @Override
    public CaptchaDTO generateLineCaptcha(String flag, int width, int height) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height);
        CaptchaDTO captchaDTO = new CaptchaDTO();
        captchaDTO.setBase64(lineCaptcha.getImageBase64());
        captchaDTO.setCaptchaId(generateCaptchaId(flag, lineCaptcha.getCode()));
        return captchaDTO;
    }

    @Override
    public boolean verifyCaptcha(String flag, String captchaId, String captcha, boolean ignoreCase) {
        String realCaptcha = getCaptcha(flag, captchaId);
        if (StrUtil.isEmpty(realCaptcha)) {
            return false;
        }
        //忽略大小写
        if (ignoreCase) {
            return realCaptcha.toLowerCase(Locale.ROOT).equals(captcha.toLowerCase(Locale.ROOT));
        }
        return realCaptcha.equals(captcha);
    }


    private String getCaptcha(String flag, String id) {
        return (String) RedisUtil.get(RedisConstants.getCaptchaCode(flag, id));
    }

    /**
     * 生成验证码标识
     * @param flag
     * @param code
     * @return
     */
    private String generateCaptchaId(String flag, String code) {
        String id = UUID.randomUUID().toString();
        RedisUtil.set(RedisConstants.getCaptchaCode(flag, id), code);
        return id;
    }




}
