package cs309_dorm_backend.service;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final RedissonClient redissonClient;

    @Autowired
    public CacheService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void putToCache(String cacheName, String key, Object value, long ttl, TimeUnit timeUnit) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(cacheName);
        mapCache.put(key, value, ttl, timeUnit);
    }

    public Object getFromCache(String cacheName, String key) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(cacheName);
        return mapCache.get(key);
    }

    public void removeFromCache(String cacheName, String key) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(cacheName);
        mapCache.remove(key);
    }
}
