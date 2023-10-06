package com.wx.auth.service.common;

import com.wx.auth.constants.AuthConstants;
import com.wx.auth.api.req.LineCaptchaRequest;
import com.wx.auth.model.vo.CaptchaVO;
import com.wx.message.api.dto.CaptchaDTO;
import com.wx.message.api.service.CaptchaServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2023/10/5 11:45
 * @Version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @DubboReference
    private CaptchaServiceApi captchaServiceApi;

    @Override
    public CaptchaVO getLineCaptcha(LineCaptchaRequest lineCaptchaRequest) {
        CaptchaDTO captchaDTO = captchaServiceApi
                .generateLineCaptcha(AuthConstants.AUTH_MODULE, lineCaptchaRequest.getWidth(), lineCaptchaRequest.getHeight());
        CaptchaVO captchaVO = new CaptchaVO();

        return (CaptchaVO) captchaVO.convert(captchaDTO);
    }

    @Override
    public boolean verifyCaptcha(String captchaId, String captcha) {
        return captchaServiceApi.verifyCaptcha(AuthConstants.AUTH_MODULE, captchaId, captcha, true);
    }

}
