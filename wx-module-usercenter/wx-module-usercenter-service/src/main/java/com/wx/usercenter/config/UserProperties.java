package com.wx.usercenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author wuweixin
 * @Date 2023/10/2 16:05
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "usercenter")
@Data
public class UserProperties {


//    private String pwdSalt;

}
