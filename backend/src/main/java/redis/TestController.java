package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private RedisTemplate foreRedisTemplate;

    @Autowired
    private RedisTemplate wechatRedisTemplate;

    @RequestMapping(value = "/testRedis", method = RequestMethod.GET)
    public String test() {
//        RedisTemplate foreRedisTemplate = redisConfig.getForeRedisTemplate();
        ValueOperations operations = foreRedisTemplate.opsForValue();
        operations.set("age", "11");

//        RedisTemplate wechatRedisTemplate = redisConfig.getWechatRedisTemplate();
        ValueOperations operations2 = wechatRedisTemplate.opsForValue();
        operations2.set("address", "bj");

        return "OK";
    }
}
