package cs309_dorm_backend.controller;

import cs309_dorm_backend.service.Verification.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/checkCode/{toEmail}")
    public String getCheckCode(@PathVariable String toEmail) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try {
            mailService.sendSimpleMail(toEmail, "SUSTech Dorm 注册验证码", checkCode);
        } catch (Exception e) {
            return "";
        }
        return checkCode;
    }
}