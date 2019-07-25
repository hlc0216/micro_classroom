package cc.mrbird.web.controller.test;

import cc.mrbird.common.annotation.Limit;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/test")
@Controller
public class TestController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到 redis的键名为 limit_test，
     * 即 prefix + "_" + key，也可以根据 IP 来限流，需指定 limitType = LimitType.IP
     */
    @Limit(key = "test", period = 60, count = 10, name = "测试Limit注解", prefix = "limit")
    @GetMapping("/test")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }


    @RequestMapping("/hello")
    public String hello(){
        return "system/test/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "system/test/index";
    }
    @RequestMapping("/java1")
    public String java1() {
        return "system/test/java1";
    }
    @RequestMapping("/ps1")
    public String ps1() {
        return "system/test/ps1";
    }

}
