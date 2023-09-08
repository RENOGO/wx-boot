package com.wx.common.mybatis.handler;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName MyBatisMetaObjectHandlerInterface.java
 * @Description TODO
 * @createTime 2022年04月25日 17:39:00
 */
public interface MyBatisMetaObjectHandlerInterface {

    String getOperatorId();

    String fillUserId(String appId, String token);
}
