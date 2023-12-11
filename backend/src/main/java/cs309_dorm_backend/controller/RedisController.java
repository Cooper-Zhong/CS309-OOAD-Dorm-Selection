package cs309_dorm_backend.controller;


import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

public class RedisController {

    private final RedisTemplate redisTemplate;

    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @AntiReptile
    @GetMapping("save")
    public Result save(String key, String value){
        redisTemplate.opsForValue().set(key, value);
        return Result.success();
    }

}
