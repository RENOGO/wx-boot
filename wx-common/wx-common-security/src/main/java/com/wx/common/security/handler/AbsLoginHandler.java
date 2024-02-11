package com.wx.common.security.handler;


/**
 * @author wwx
 * @date 2021/11/25 16:14
 * @description 实现ILoginHandler的类不一定会用到他所有方法，根据设计模式-接口隔离原则，这样不合理，所以这里有个中间的抽象类
 */
public abstract class AbsLoginHandler implements ILoginHandler {

//
//    /**
//     * 登录请求发起前
//     *
//     * @param loginRequest
//     */
//    @Override
//    public void before(LoginRequest loginRequest) {
//        UmsAdminService
//    }
//
//
//    /**
//     * 登录成功
//     * @param loginRequest
//     * @param response
//     */
//    @Override
//    public void success(LoginRequest loginRequest, LoginUser response) {
//
//    }
//
//    /**
//     * 登录失败
//     *
//     * @param loginRequest
//     * @param e
//     */
//    @Override
//    public void error(LoginRequest loginRequest, Exception e) {
//
//    }
//
//    /**
//     * 自己处理登录的接口请求
//     *
//     * @param loginRequest
//     * @return
//     */
//    @Override
//    public LoginUser login(LoginRequest loginRequest) {
//        return RemoteUserInfoUtil.loginByAccount(loginRequest);
//    }
}
