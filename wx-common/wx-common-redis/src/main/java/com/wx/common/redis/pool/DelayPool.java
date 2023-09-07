package com.wx.common.redis.pool;

import com.wx.common.core.thread.AppThreadPool;
import com.wx.common.redis.util.RedisUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wuweixin
 * @Date 2022/3/7 14:52
 * @Version 1.0
 */
@Component
public class DelayPool extends AppThreadPool {

    public static final String REDIS_DELAY_POOL = "redis-delay-thread-pool";

    public DelayPool() {
        super(REDIS_DELAY_POOL);
    }

    public void run(String[] key, long delay) {
        super.run(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {

                }
                RedisUtil.del(key);
            }
        });
    }
}
