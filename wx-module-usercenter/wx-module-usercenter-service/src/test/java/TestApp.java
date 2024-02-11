import com.wx.message.api.service.CaptchaServiceApi;
import com.wx.usercenter.UsercenterApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author wuweixin
 * @Date 2023/9/21 21:47
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = UsercenterApplication.class)
public class TestApp {


    @DubboReference
    private CaptchaServiceApi captchaServiceApi;

    @Test
    public void test1() {
        boolean b = captchaServiceApi.verifyCaptcha("1", "2", "1", true);
        log.info(b + "结果");
    }

}
