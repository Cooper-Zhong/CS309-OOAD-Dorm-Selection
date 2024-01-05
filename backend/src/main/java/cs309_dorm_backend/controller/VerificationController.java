package cs309_dorm_backend.controller;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.VerificationDto;
import cs309_dorm_backend.service.Verification.MailService;
import cs309_dorm_backend.service.Verification.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/verification")
@Slf4j
public class VerificationController {

    private final MailService mailService;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public VerificationController(MailService mailService, VerificationCodeService verificationCodeService) {
        this.mailService = mailService;
        this.verificationCodeService = verificationCodeService;
    }


    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/getCode/{toEmail}")
    public String getCode(@PathVariable String toEmail) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try {
            mailService.sendSimpleMail(toEmail, "SUSTech Dorm 注册验证码", checkCode);
        } catch (Exception e) {
            log.error("邮件发送失败 to:{}", toEmail);
            throw new MyException(1, "邮件发送失败");
        }
        try {
            verificationCodeService.storeVerificationCode(toEmail, checkCode);
        } catch (Exception e) {
            log.error("验证码存储失败 to:{}", toEmail);
            log.error(e.getMessage());
            throw new MyException(2, "验证码存储失败");
        }
        log.info("验证码生成并存储成功 to:{}", toEmail);
        return checkCode;
    }

    @PostMapping("/verifyCode")
    public GlobalResponse<String> verifyCode(@RequestBody VerificationDto verificationDto) {
        String email = verificationDto.getEmail();
        String codeFromFrontend = verificationDto.getVerificationCode();
        // Get the saved verification code from the cache
        String savedCode = verificationCodeService.getVerificationCode(email);

        if (savedCode != null && savedCode.equals(codeFromFrontend)) {
            log.info("Code is correct: " + email);
            return new GlobalResponse<>(0, "Code is correct: " + email, null);
        } else {
            // Verification code is incorrect or expired
            log.warn("Code is incorrect or expired: " + email);
            return new GlobalResponse<>(1, "Code is incorrect or expired: " + email, null);
        }
    }
}
