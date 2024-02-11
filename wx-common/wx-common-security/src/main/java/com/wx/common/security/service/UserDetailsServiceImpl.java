package com.wx.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wuweixin
 */
//@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        SecurityUser userDetails = new SecurityUser();
//        userDetails.setPassword("123");
//        userDetails.setUsername("username123");
//        return userDetails;
        return null;
    }
}
