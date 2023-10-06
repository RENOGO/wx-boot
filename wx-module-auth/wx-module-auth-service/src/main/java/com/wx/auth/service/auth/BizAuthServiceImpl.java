package com.wx.auth.service.auth;

import cn.hutool.core.bean.BeanUtil;
import com.wx.auth.api.dto.TokenDTO;
import com.wx.auth.api.req.LoginRequest;
import com.wx.auth.enums.ResponseEnums;
import com.wx.auth.model.vo.LoginResVO;
import com.wx.auth.service.common.MessageService;
import com.wx.auth.service.common.TokenService;
import com.wx.common.security.utils.PasswordUtil;
import com.wx.common.utils.AssertUtil;
import com.wx.usercenter.api.dto.UserDTO;
import com.wx.usercenter.api.service.UserServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/9/21 23:03
 * @Version 1.0
 */
@Service
public class BizAuthServiceImpl implements BizAuthService {

    @DubboReference
    private UserServiceApi userServiceApi;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokenService tokenService;

    @Override
    public LoginResVO login(LoginRequest loginRequest) {
        AssertUtil.xAssert(!messageService.verifyCaptcha(loginRequest.getCaptchaId(), loginRequest.getCaptcha()),
                ResponseEnums.CAPTCHA_INVALID);
        UserDTO user = userServiceApi.getUserByUsername(loginRequest.getUsername());
        AssertUtil.xAssert(user == null || !PasswordUtil.matches(user.getPassword(), loginRequest.getPassword(), null),
                ResponseEnums.LOGIN_FAIL);
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId());
        TokenDTO tokenDTO = tokenService.generalToken(user.getId(), payload, BeanUtil.beanToMap(user));
        LoginResVO loginResVO = new LoginResVO();
        return (LoginResVO) loginResVO.convert(tokenDTO);
    }


}
