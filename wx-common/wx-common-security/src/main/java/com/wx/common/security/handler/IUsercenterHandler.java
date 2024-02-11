package com.wx.common.security.handler;


/**
 * @author wwx
 * @date 2021/11/25 16:56
 * @description 防止循环调用接口的一个Handler，只由用户中心实现，其他服务不需要实现
 */
public interface IUsercenterHandler {

    /**
     * 获得登录用户的信息
     *
     * @return
     */
//    LoginUser getLoginUserInfo(String appId, String token);
}
