package com.wx.common.master.redis.thread;


import com.wx.common.master.redis.config.NodeConfig;
import com.wx.common.master.redis.constants.NodeConstants;
import com.wx.common.master.redis.listener.MasterChangeListener;
import com.wx.common.master.redis.main.NodeManager;
import com.wx.common.master.redis.model.NodeInfo;

/**
 * @Author wuweixin
 * @Date 2023/3/13 14:54
 * @Version 1.0
 */
public class ScrambleMasterThread extends Thread {

    private MasterChangeListener masterChangeListener;

    private String masterUnique;

    private String unique;

    private NodeConfig nodeConfig;

    public ScrambleMasterThread(NodeConfig nodeConfig, MasterChangeListener masterChangeListener) {
        this.masterChangeListener = masterChangeListener;
        this.unique = nodeConfig.getUnique();
        this.nodeConfig = nodeConfig;
    }

    @Override
    public void run() {
        while (true) {
            NodeInfo nodeInfo = NodeManager.getInstance().scrambleMaster();
            if (nodeInfo == null) {
                masterUnique = null;
                if (this.masterChangeListener != null) {
                    this.masterChangeListener.change(false, null);
                }
            } else {
                String newUnique = nodeInfo.getUnique();
                if (masterUnique == null || !masterUnique.equals(newUnique)) {
                    masterUnique = newUnique;
                    if (this.masterChangeListener != null) {
                        this.masterChangeListener.change(unique.equals(masterUnique), nodeInfo);
                    }
                }
            }
            try {
                Thread.sleep(NodeConstants.DEFAULT_CHECK_TIME);
            } catch (InterruptedException e) {

            }
        }
    }
}
