package com.wx.common.master.redis.main;


import com.wx.common.lock.redis.utils.RedissonLockUtil;
import com.wx.common.master.redis.config.NodeConfig;
import com.wx.common.master.redis.constants.NodeConstants;
import com.wx.common.master.redis.listener.MasterChangeListener;
import com.wx.common.master.redis.model.NodeInfo;
import com.wx.common.master.redis.thread.AliveThread;
import com.wx.common.master.redis.thread.ScrambleMasterThread;
import com.wx.common.redis.util.RedisUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author wx
 * @Date 2023/3/13 14:16
 * @Version 1.0
 */
public class NodeManager {

    private static final NodeManager INSTANCE = new NodeManager();
    private boolean init = false;
    private NodeConfig nodeConfig;
    private static final Object LOCK = new Object();
    private String unique;

    private String address;
    private NodeInfo nodeInfo;

    public static NodeManager getInstance() {
        return INSTANCE;
    }


    private NodeManager() {

    }


    public void init(NodeConfig config,
                     MasterChangeListener masterChangeListener) {
        synchronized (LOCK) {
            if (this.init) {
                return;
            }
            if (config.getPort() == null) {
                throw new RuntimeException("缺少port");
            }
            this.address = config.getHost() + ":" + config.getPort();
            this.nodeConfig = config;
            this.init = true;
            this.unique = config.getUnique();
            if (StringUtils.isEmpty(this.unique)) {
                throw new RuntimeException("缺少位唯一示");
            }
            this.nodeInfo = new NodeInfo();
            nodeInfo.setUnique(unique);
            nodeInfo.setAddress(address);
            nodeInfo.setExtra(nodeConfig.getExtra());
            new ScrambleMasterThread(nodeConfig, masterChangeListener).start();
            if (nodeConfig.isSendHeartBeat()) {
                new AliveThread().start();
            }
        }
    }


    /**
     * 获得当前节点的ip+端口
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 获得当前服务的标识
     *
     * @return
     */
    public String getUnique() {
        return unique;
    }

    /**
     * 判断当前是否为主节点
     *
     * @return
     */
    public boolean isMaster() {
        return unique.equals(getMasterUnique());
    }


    /**
     * 获得主节点的信息
     *
     * @return
     */
    public NodeInfo getMaster() {
        return (NodeInfo) RedisUtil.get(NodeConstants.MASTER);
    }


    /**
     * 获得主节点的唯一标识
     *
     * @return
     */
    public String getMasterUnique() {
        NodeInfo master = getMaster();
        if (master == null) {
            return null;
        }
        return master.getUnique();
    }

    /**
     * 获得还存活的节点
     *
     * @return
     */
    public Map<String, NodeInfo> getAliveNode() {
        Set<String> keys = RedisUtil.keys(NodeConstants.NODE_LIST + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        Map<String, NodeInfo> res = new HashMap<>();
        for (String key : keys) {
            NodeInfo node = (NodeInfo) RedisUtil.get(key);
            if (node != null) {
                res.put(node.getUnique(), node);
            }
        }
        return res;
    }


    /**
     * 发送心跳
     */
    public void sendHeartBeat() {
        RedisUtil.set(NodeConstants.getNodeRedisKey(unique), this.nodeInfo, NodeConstants.DEFAULT_MASTER_EXPIRED);
    }

    /**
     * 争抢master
     *
     * @return 如果这里返回null，说明master在争抢中
     */
    public NodeInfo scrambleMaster() {
        NodeInfo master = getMaster();
        //没有master就开始抢
        if (master == null) {
            //尝试抢主节点，拿不到就直接返回
            boolean success = RedissonLockUtil.tryLock(NodeConstants.LOCK_NAME, TimeUnit.SECONDS, 0);
            if (!success) {
                return null;
            }
            setMaster();
            RedissonLockUtil.unlock(NodeConstants.LOCK_NAME);
        } else {
            //自己就是master，那就给自己续时间。
            if (this.unique.equals(master.getUnique())) {
                // 非常极端下：主节点一直卡着没续上心跳，等阻塞完了，把别人的主节点给抢了，这种情况原来的主节点会自动退出为从节点，在这期间可能会有10秒钟的时间出现双主节点的情况
                // 如果要把这种非常极端的情况也考虑，那么分布式锁得用到整个代码块，但这种极端情况一般也是由于网络问题，此时看门狗也可能没法续上时间，同样还是可能出现上述问题
                // 所以这里真正应该处理的是设置超时时间小于心跳过期时间
                setMaster();
            }
        }
        return master;
    }

    /**
     * 设置主节点
     */
    private void setMaster() {
        RedisUtil.set(NodeConstants.MASTER, nodeInfo, NodeConstants.DEFAULT_MASTER_EXPIRED);
    }


}
