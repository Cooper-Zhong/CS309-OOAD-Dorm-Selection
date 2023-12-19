package cs309_dorm_backend.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.service.message.MessageService;
import cs309_dorm_backend.service.message.MessageServiceImpl;
import cs309_dorm_backend.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @描述 WebSocket核心配置类
 * @创建人 haoqian
 * @创建时间 2021/5/20
 */

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端。
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/api/websocket/{sid}")
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>(); // studentId -> WebSocketServer

    @Autowired
    private MessageService messageService = new MessageServiceImpl();

    @Autowired
    private NotificationService notificationService;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     // 加入set中
        webSocketMap.put(sid, this);
        this.sid = sid;
        addOnlineCount();           // 在线数加1
        try {
            sendData("fuck you");
            pushMessages();
            pushNotifications();
            log.info("有新客户端开始监听,sid=" + sid + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    public void pushMessages() {
        List<Message> messageList = messageService.findByReceiverId(this.sid);
        try {
            sendData(JSON.toJSONString(messageList));
        } catch (IOException e) {
            throw new MyException(4, "push messages to " + sid + " failed");
        }
    }

    public void pushNotifications() {
        List<Notification> notificationList = notificationService.findByReceiverId(this.sid);
        try {
            sendData(JSON.toJSONString(notificationList));
        } catch (IOException e) {
            throw new MyException(4, "push notifications to " + sid + " failed");
        }

    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        webSocketMap.remove(this.sid);
        subOnlineCount();              // 在线数减1
        // 断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid=" + sid + "的客户端");
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端 sid=" + sid + " 的信息:" + message);
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息给指定sid
     */
    public static void sendData(String message, HashSet<String> toSids) {
        WebSocketServer target = null;
        log.info("推送消息到客户端 " + toSids + "，推送内容:" + message);
        for (String sid : toSids) {
            try {
                target = webSocketMap.get(sid);
                if (target != null) {
                    target.sendData(message);
                }
            } catch (IOException e) {
                continue;
            }
        }

//        for (WebSocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给传入的sid，为null则全部推送
//                if (toSids.size()<=0) {
//                    item.sendData(message);
//                } else if (toSids.contains(item.sid)) {
//                    item.sendData(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
    }

    /**
     * 发自定义消息给指定sid
     */
    public static void sendData(String message, String toSid) {
        WebSocketServer target = null;
        try {
            target = webSocketMap.get(toSid);
            if (target != null) {
                target.sendData(message);
                log.info("推送消息到客户端 " + toSid + "，推送内容:" + message);
            }
        } catch (IOException e) {
            throw new MyException(4, "websocket send data error");
        }
    }

    public static void sendData(JSONObject json, String toSid) {
        sendData(json.toString(), toSid);
    }

    public static void sendData(JSONObject json, HashSet<String> toSids) {
        sendData(json.toString(), toSids);
    }


    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendData(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendData(JSONObject json) throws IOException {
        sendData(json.toString());
    }


    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}
