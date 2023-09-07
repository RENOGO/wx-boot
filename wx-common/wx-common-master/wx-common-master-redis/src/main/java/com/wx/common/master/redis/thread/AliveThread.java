package com.wx.common.master.redis.thread;


import com.wx.common.master.redis.config.NodeConfig;
import com.wx.common.master.redis.constants.NodeConstants;
import com.wx.common.master.redis.main.NodeManager;

/**
 * @Author wuweixin
 * @Date 2023/3/15 14:00
 * @Version 1.0
 */
public class AliveThread extends Thread {


    private NodeConfig nodeConfig;

    public AliveThread() {
    }


    @Override
    public void run() {
        while (true) {
            NodeManager.getInstance().sendHeartBeat();
            try {
                Thread.sleep(NodeConstants.DEFAULT_MASTER_EXPIRED * 1000 / 2);
            } catch (InterruptedException e) {

            }
        }

    }
}
