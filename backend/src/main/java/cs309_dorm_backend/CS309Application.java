package cs309_dorm_backend;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableCaching
@EnableSwagger2
@CrossOrigin(origins = "*") // 允许所有域的跨域请求
public class CS309Application {
    // remember to create trigger in database first.
//    private RedissonClient redissonClient;
//
//    @Autowired
//    public CS309Application(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
    public static void main(String[] args) {
        SpringApplication.run(CS309Application.class, args);
    }
}
