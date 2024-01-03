package cs309_dorm_backend.websocket;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @描述 WebSocket消息推送控制器
 * @创建人 haoqian
 * @创建时间 2021/5/20
 */
@RestController
public class SystemController {

    // 推送数据到websocket客户端 接口
    @PostMapping("/socket/push/{cid}")
    public Map pushMessage(@PathVariable("cid") String cid, String message) {
        Map<String, Object> result = new HashMap<>();
        HashSet<String> sids = new HashSet<>();
        sids.add(cid);
        MessageWebSocketServer.sendData("服务端推送消息：" + message, sids);
        result.put("code", cid);
        result.put("msg", message);
        return result;
    }
}
