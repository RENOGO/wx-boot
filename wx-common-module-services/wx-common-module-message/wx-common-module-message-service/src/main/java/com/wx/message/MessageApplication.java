package com.wx.message;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wuweixin
 * @Date 2023/10/2 17:47
 * @Version 1.0
 */
@EnableDubbo
@SpringBootApplication
public class MessageApplication {


    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }


}
