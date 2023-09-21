package com.wx.common.master.sr.config;

import cn.hutool.core.bean.BeanUtil;
import com.wx.common.master.redis.config.NodeConfig;
import com.wx.common.master.redis.listener.MasterChangeListener;
import com.wx.common.master.redis.main.NodeManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wx
 * @Date 2023/9/12 14:16
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(MasterRedisProperties.class)
public class MasterRedisConfig {


    @Bean
    @ConditionalOnBean(MasterChangeListener.class)
    public NodeManager nodeManager(MasterRedisProperties properties, MasterChangeListener masterChangeListener) {
        NodeConfig nodeConfig = new NodeConfig();
        BeanUtil.copyProperties(properties, nodeConfig);
        return NodeManager.getInstance().init(nodeConfig, masterChangeListener);
    }

}
