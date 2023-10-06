import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.junit.jupiter.api.Test;

/**
 * @Author wuweixin
 * @Date 2023/10/2 17:27
 * @Version 1.0
 */
public class TestModule {

    @Test
    public void test1() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        System.out.println(lineCaptcha.getImageBase64());
        System.out.println( lineCaptcha.getImageBase64Data());
////图形验证码写出，可以写出到文件，也可以写出到流
//        lineCaptcha.write("/Users/wuweixin/program/test111.png");
////输出code
//        Console.log(lineCaptcha.getCode());
////验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");
//
////重新生成验证码
//        lineCaptcha.createCode();
//        lineCaptcha.write("/Users/wuweixin/program/line.png");
////新的验证码
//        Console.log(lineCaptcha.getCode());
////验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");

    }

}
