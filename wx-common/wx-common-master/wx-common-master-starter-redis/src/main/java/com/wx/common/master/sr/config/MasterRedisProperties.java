package com.wx.common.master.sr.config;

import com.wx.common.constants.PropertiesPre;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @Author wx
 * @Date 2023/9/12 14:20
 * @Version 1.0
 */
@ConfigurationProperties(prefix = PropertiesPre.MASTER_REDIS)
public class MasterRedisProperties {


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
     * 是否发送心跳，如果不需要做节点管理的可以不传
     */
    private boolean sendHeartBeat;

    /**
     * 额外的节点信息
     */
    private Map<String, Object> extra;

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getHost() {
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

    public boolean isSendHeartBeat() {
        return sendHeartBeat;
    }

    public void setSendHeartBeat(boolean sendHeartBeat) {
        this.sendHeartBeat = sendHeartBeat;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
