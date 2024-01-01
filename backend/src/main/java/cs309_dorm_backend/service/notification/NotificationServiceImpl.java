package cs309_dorm_backend.service.notification;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.NotificationRepo;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.dto.NotificationDto;
import cs309_dorm_backend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserService userService;

    @Override
    public Notification createAndSaveNotification(String type, String receiverId, String content) {
        Notification notification = Notification.builder()
                .type(type)
                .receiver(userService.findByCampusId(receiverId))
                .read(false)
                .content(content)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return notificationRepo.save(notification);
    }

    @Override
    public Notification findById(int notificationId) {
        return notificationRepo.findById(notificationId).orElse(null);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepo.findAll();
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepo.save(notification);
    }

    @Override
    public List<Notification> findByReceiverId(String receiverId) {
        return notificationRepo.findByReceiverId(receiverId);
    }

    @Override
    @Transactional
    public boolean deleteById(int notificationId) {
        try {// add transactional to update/delete operation
            notificationRepo.deleteById(notificationId);
            return true;
        } catch (Exception e) {
            throw new MyException(4, "notification " + notificationId + " does not exist");
        }
    }

    @Override
    @Transactional
    public boolean deleteByReceiverId(String receiverId) {
        try {
            notificationRepo.deleteByReceiverId(receiverId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException(4, "notification belonging to " + receiverId + " does not exist");
        }
    }

    @Override
    public boolean read(int notificationId) {
        try {
            Notification notification = notificationRepo.findById(notificationId).orElse(null);
            notification.setRead(true);
            notificationRepo.save(notification);
            return true;
        } catch (Exception e) {
            throw new MyException(4, "notification " + notificationId + " does not exist");
        }
    }

    @Override
    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .notificationId(notification.getNotificationId())
                .type(notification.getType())
                .receiverId(notification.getReceiver().getCampusId())
                .read(notification.isRead())
                .time(notification.getTime())
                .content(notification.getContent())
                .build();
    }
}
