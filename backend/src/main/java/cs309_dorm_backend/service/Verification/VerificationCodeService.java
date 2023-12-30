package cs309_dorm_backend.service.Verification;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    public VerificationCodeService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeVerificationCode(String email, String verificationCode) {
        redisTemplate.opsForValue().set(email, verificationCode, 5, TimeUnit.MINUTES);
    }

    public String getVerificationCode(String email) {
        return redisTemplate.opsForValue().get(email);
    }
}
