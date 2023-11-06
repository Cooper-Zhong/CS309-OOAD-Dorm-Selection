package cs309_dorm_backend.web;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
//
//@RestController
//public class hello {
//    @RequestMapping("/hello")
//    public String hello(){
//        return "Hello world";
//    }
//}

public class hello {

    private int a = 1;
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        int val = map.get("b");
        System.out.println(val);
    }
}

