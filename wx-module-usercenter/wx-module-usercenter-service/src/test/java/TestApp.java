import com.wx.usercenter.UsercenterApplication;
import com.wx.usercenter.api.service.UserServiceApi;
import com.wx.usercenter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void test1() {
        userServiceApi.getUserByUsername("!");
    }


}
