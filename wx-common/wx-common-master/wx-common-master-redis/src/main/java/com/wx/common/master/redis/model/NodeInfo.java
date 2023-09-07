package com.wx.common.master.redis.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/3/15 10:55
 * @Version 1.0
 */
public class NodeInfo implements Serializable {


    private String unique;

    private String address;

    private Map<String, Object> extra;

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
