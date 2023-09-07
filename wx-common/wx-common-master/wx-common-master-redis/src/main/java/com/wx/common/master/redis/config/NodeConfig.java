package com.wx.common.master.redis.config;

import com.wx.common.utils.IpUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/3/13 14:19
 * @Version 1.0
 */
public class NodeConfig {

    /**
     * 写入当前节点的唯一标识，默认是ip:port
     */
    private String unique;

    /**
     * 节点ip
     */
    private String host;

    /**
     * 节点端口
     */
    private Integer port;

    /**
     * 是否发生心跳，如果不需要做节点管理的可以不传
     */
    private boolean sendHeartBeat;

    /**
     * 额外的节点信息
     */
    private Map<String, Object> extra;

    public boolean isSendHeartBeat() {
        return sendHeartBeat;
    }

    public void setSendHeartBeat(boolean sendHeartBeat) {
        this.sendHeartBeat = sendHeartBeat;
    }

    public String getUnique() {
        if (StringUtils.hasText(unique)) {
            unique = getHost() + ":" + getPort();
        }
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getHost() {
        if (StringUtils.hasText(host)) {
            host = IpUtil.getHost();
        }
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
