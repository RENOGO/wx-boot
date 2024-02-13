package com.wx.authorization.service.login;

import com.common.token.base.model.LoginUser;
import com.common.token.base.model.TokenUserInfo;
import com.common.token.base.utils.LoginUtil;
import com.wx.authorization.api.request.LoginRequest;
import com.wx.authorization.constants.AuthorizationConstants;
import com.wx.authorization.constants.ResponseStatusEnum;
import com.wx.common.utils.AssertUtil;
import com.wx.message.api.dto.CaptchaDTO;
import com.wx.message.api.service.CaptchaServiceApi;
import com.wx.usercenter.api.dto.SysUserDTO;
import com.wx.usercenter.api.service.UserServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author wuweixin
 * @Date 2024/2/7 09:28
 * @Version 1.0
 * @Descritube
 */
@Service
public class LoginServiceImpl implements LoginService {

    @DubboReference
    private UserServiceApi userServiceApi;

    @DubboReference
    private CaptchaServiceApi captchaServiceApi;

    @Override
    public LoginUser loginUser(LoginRequest loginRequest) {
        AssertUtil.xAssert(captchaServiceApi
                        .verifyCaptcha(AuthorizationConstants.MODULE_NAME,
                                loginRequest.getCaptchaId(), loginRequest.getCaptcha(), true),
                ResponseStatusEnum.CAPTCHA_CODE_ERROR);
        SysUserDTO user = userServiceApi.getUserByUsername(loginRequest.getUsername());
        AssertUtil.xAssert(user == null || !loginRequest.getPassword().equals(user.getPassword()),
                ResponseStatusEnum.USER_PWD_ERROR);
        TokenUserInfo tokenUserInfo = new TokenUserInfo();
        tokenUserInfo = (TokenUserInfo) tokenUserInfo.convert(user);

        return LoginUtil.login(user.getId(), tokenUserInfo);
//        TokenUserInfo tokenUserInfo = new TokenUserInfo();
//        tokenUserInfo.setId("admin");
//        return LoginUtil.login("admin", tokenUserInfo);
    }

    @Override
    public CaptchaDTO generateLineCaptcha(int width, int height) {
        return captchaServiceApi.generateLineCaptcha(AuthorizationConstants.MODULE_NAME, width, height);
    }
}
