package cs309_dorm_backend.controller;

import cn.keking.anti_reptile.annotation.AntiReptile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    //当用户从浏览器发送请求访问 /in 接口时
    // 服务端会返回 302 响应码
    // 让客户端重定向到 /login 页面
    // 用户在 /login 页面登录，登陆成功之后
    // 就会自动跳转到 /in 接口。


    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @AntiReptile
    @GetMapping("/in")
    public String hello() {
        return "Login Success";
    }

}
