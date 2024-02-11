package com.wx.common.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wwx
 * @date 2021/11/15 13:49
 * @description
 */
@Component
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }

//
//    @Autowired
//    private RemoteLoginApi remoteLoginApi;
//
//    @Override
//    public void logout(HttpServletRequest httpServletRequest,
//                       HttpServletResponse httpServletResponse,
//                       Authentication authentication) {
//        try {
////            String token = LoginUserUtil.getToken();
////            Assert.isFalse(StringUtils.isEmpty(token), "缺少鉴权凭证");
////            String appId = LoginUserUtil.getXcKey();
//            //通知用户中心这个用户登出
//            remoteLoginApi.logout();
//            //然后再删除本地redis对这个用户的缓存
//            LoginUserUtil.delCurrentToken();
//        } catch (Exception e) {
//            String message = "登出失败";
//            if (e instanceof DecodeException) {
//                DecodeException decodeException = (DecodeException) e;
//                message = decodeException.getMessage();
//            }
//            ResponseUtils.out(httpServletResponse, ResultGenerator.genFailResult(message));
//            return;
//        }
//        ResponseUtils.out(httpServletResponse, ResultGenerator.genSuccessResult());
//    }

}
