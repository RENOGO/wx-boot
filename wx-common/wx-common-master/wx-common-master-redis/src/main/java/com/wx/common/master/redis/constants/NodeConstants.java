package com.wx.common.master.redis.constants;

/**
 * @Author wx
 * @Date 2023/3/13 14:23
 * @Version 1.0
 */
public class NodeConstants {

    public static final String MASTER_ELECTOR_PREFIX = "wx-boot:node";
    public static final String LOCK_NAME = MASTER_ELECTOR_PREFIX + ":lock";

    public static final String MASTER = MASTER_ELECTOR_PREFIX + ":master";

    public static final String NODE_LIST = MASTER_ELECTOR_PREFIX + ":node_list:";

    /**
     * master 60秒都没有响应，就切换
     */
    public static final int DEFAULT_MASTER_EXPIRED = 60;


    /**
     * 每10秒检查一次master是否存活
     */
    public static final int DEFAULT_CHECK_TIME = 10 * 1000;


    public static String getNodeRedisKey(String unique) {
        return NODE_LIST + unique;
    }

}
