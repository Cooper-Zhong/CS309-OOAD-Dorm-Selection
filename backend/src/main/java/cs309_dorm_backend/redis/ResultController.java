//package cs309_dorm_backend.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RedisHash("result")
//public class ResultController {
//
//    @Autowired
//    private RedisConfig redisConfig;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @RequestMapping(value = "/testRedis", method = RequestMethod.GET)
//    public String test() {
//        ValueOperations operations = redisTemplate.opsForValue();
//        operations.set("age", "11");
//
//        return "OK";
//    }
//
//    @RequestMapping(value = "/getRedis", method = RequestMethod.GET)
//    public String getRedis() {
//        ValueOperations operations = redisTemplate.opsForValue();
//        String age = (String) operations.get("age");
//        return age;
//    }
//
//    @RequestMapping(value = "/testRedisConfig", method = RequestMethod.GET)
//    public String testRedisConfig() {
//        ValueOperations operations = redisConfig.redisTemplate().opsForValue();
//        operations.set("age", "11");
//
//        return "OK";
//    }
//}
