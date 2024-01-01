package cs309_dorm_backend.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.dto.MessageDto;
import cs309_dorm_backend.service.message.MessageService;
import cs309_dorm_backend.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Comparator;
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
//@Component
@Slf4j
@Service
@ServerEndpoint("/api/websocket/message/{sid}")
public class MessageWebSocketServer {


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<MessageWebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentMap<String, MessageWebSocketServer> webSocketMap = new ConcurrentHashMap<>(); // studentId -> WebSocketServer


    private static MessageService messageService;


    // websocket是多对象的，而bean默认是单例的，所以需要用static修饰
    @Autowired
    public void setMessageService(MessageService messageService) {
        MessageWebSocketServer.messageService = messageService;
    }


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
        pushMessages();
        log.info("有新客户端开始监听,sid=" + sid + ",当前在线人数为:" + getOnlineCount());
    }

    public void pushMessages() {
        log.info("push messages to: " + sid);
        List<Message> messageList = messageService.findBySenderIdAndReceiverId(this.sid, this.sid);
        try {
            messageList.sort(Comparator.comparing(Message::getTime));
            for (Message message : messageList) {
                sendData(JSON.toJSONString(messageService.toDto(message)));
                log.debug("push：" + message.getSender().getCampusId() + " -> " + message.getReceiver().getCampusId() + " " + message.getContent());
            }
        } catch (IOException e) {
            throw new MyException(4, "push messages to " + sid + " failed");
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
        MessageWebSocketServer target = null;
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
    }

    /**
     * 发自定义消息给指定sid
     */
    public static void sendData(String message, String toSid) {
        MessageWebSocketServer target = null;
        try {
            target = webSocketMap.get(toSid);
            if (target != null) {
                target.sendData(message);
                log.info("push notification to " + toSid);
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
     * 实现服务器主动推送消息到 当前客户端
     */
    public void sendData(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendData(Object message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(message);
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
    public static CopyOnWriteArraySet<MessageWebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}
