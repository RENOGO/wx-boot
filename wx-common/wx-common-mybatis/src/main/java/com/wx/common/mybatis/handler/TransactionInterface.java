package com.wx.common.mybatis.handler;

/**
 * @Author wx
 * @Date 2023/2/2 19:06
 * @Version 1.0
 */
public interface TransactionInterface {

    void transaction();

    void exception(Exception e);

}
