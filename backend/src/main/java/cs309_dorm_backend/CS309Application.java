package cs309_dorm_backend;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
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
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://10.26.80.100:6379") // 替换为您的Redis服务器地址
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(5);

        RedissonClient redisson = Redisson.create(config);
        redisson.getKeys();
        SpringApplication.run(CS309Application.class, args);
        redisson.shutdown();
    }

}
