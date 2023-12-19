package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/deleteById/{notificationId}")
    public boolean deleteById(@PathVariable int notificationId) {
        return notificationService.deleteById(notificationId);
    }

    @GetMapping("/deleteByReceiverId/{receiverId}")
    public boolean deleteByReceiverId(@PathVariable String receiverId) {
        return notificationService.deleteByReceiverId(receiverId);
    }

    @GetMapping("/findById/{notificationId}")
    public Notification findById(@PathVariable int notificationId) {
        return notificationService.findById(notificationId);
    }

}
