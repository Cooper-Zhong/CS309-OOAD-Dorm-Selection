//package cs309_dorm_backend.redis;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedissonConfig {
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://10.26.80.100:6379") // 替换为您的Redis服务器地址
//                .setConnectionPoolSize(10)
//                .setConnectionMinimumIdleSize(5);
//
//        return Redisson.create(config);
//    }
//}