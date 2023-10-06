import cn.hutool.jwt.JWT;
import com.wx.common.auth.dto.TokenDTO;
import com.wx.common.auth.service.TokenService;
import com.wx.usercenter.UsercenterApplication;
import com.wx.usercenter.api.service.UserServiceApi;
import com.wx.usercenter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:47
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = UsercenterApplication.class)
public class TestApp {

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceApi userServiceApi;

    @Autowired
    private TokenService tokenService;

    @Test
    public void test1() {
        userServiceApi.getUserByUsername("!");
    }


    @Test
    public void test2() {
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> userinfo = new HashMap<>();
        payload.put("pay", "111");
        userinfo.put("user", "222");
        TokenDTO tokenDTO = tokenService.generalToken("666", payload, userinfo);
        JWT jwt = tokenService.parseToken(tokenDTO.getAccessToken());
        System.out.println();

    }

}
