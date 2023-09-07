package com.wx.common.master.redis.listener;


import com.wx.common.master.redis.model.NodeInfo;

/**
 * @Author wx
 * @Date 2023/3/13 14:59
 * @Version 1.0
 */
public interface MasterChangeListener {


    /**
     *
     * @param isMaster
     * @param masterInfo
     */
    void change(boolean isMaster, NodeInfo masterInfo);

}
