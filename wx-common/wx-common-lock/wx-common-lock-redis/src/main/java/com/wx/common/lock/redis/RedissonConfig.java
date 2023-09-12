package com.wx.common.lock.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author wx
 */
@Deprecated
//@Configuration
//@ConditionalOnProperty(name = ".yml-config")
public class RedissonConfig {


    @Value("${}")
    private String ymlConfig;


    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource(ymlConfig).getInputStream());
        return Redisson.create(config);
    }
}
