package com.wx.usercenter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wuweixin
 * @Date 2023/9/21 15:53
 * @Version 1.0
 */
@MapperScan("com.wx.usercenter.mapper")
@EnableDubbo
@SpringBootApplication
public class UsercenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsercenterApplication.class, args);
    }

}
