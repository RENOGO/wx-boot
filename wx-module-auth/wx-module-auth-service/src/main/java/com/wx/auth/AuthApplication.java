package com.wx.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wuweixin
 * @Date 2023/9/26 15:36
 * @Version 1.0
 */
@MapperScan("com.wx.auth.mapper")
@SpringBootApplication
public class AuthApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
