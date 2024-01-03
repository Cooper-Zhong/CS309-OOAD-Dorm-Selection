package cs309_dorm_backend.controller;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {


    @Autowired
    private NotificationService notificationService;

    @GetMapping("/read/{notificationId}")
    public boolean readNotification(@PathVariable int notificationId) {
        return notificationService.read(notificationId);
    }

    @GetMapping("/findAll")
    public List<Notification> findAll() {
        return notificationService.findAll();
    }

    @GetMapping("/findByReceiverId/{receiverId}")
    public List<Notification> findByReceiverId(@PathVariable String receiverId) {
        return notificationService.findByReceiverId(receiverId);
    }

    @DeleteMapping("/deleteById/{notificationId}")
    public boolean deleteById(@PathVariable int notificationId) {
        return notificationService.deleteById(notificationId);
    }

    @DeleteMapping("/deleteByReceiverId/{receiverId}")
    public boolean deleteByReceiverId(@PathVariable String receiverId) {
        return notificationService.deleteByReceiverId(receiverId);
    }

    @GetMapping("/findById/{notificationId}")
    public Notification findById(@PathVariable int notificationId) {
        return notificationService.findById(notificationId);
    }

}
