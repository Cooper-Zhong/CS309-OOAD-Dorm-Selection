package cs309_dorm_backend.service.Verification;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private RedissonClient redissonClient;

    public void storeVerificationCode(String email, String verificationCode) {
        redissonClient.getBucket(email).set(verificationCode, 5, TimeUnit.MINUTES);
    }

    public String getVerificationCode(String email) {
        return (String) redissonClient.getBucket(email).get();
    }
}
