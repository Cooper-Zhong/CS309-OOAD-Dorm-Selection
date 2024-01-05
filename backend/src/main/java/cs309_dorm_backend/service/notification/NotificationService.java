package cs309_dorm_backend.service.notification;

import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    Notification createAndSaveNotification(String type, String receiverId, String content);

    Notification findById(int notificationId);

    List<Notification> findAll();

    Notification save(Notification notification);

    List<Notification> findByReceiverId(String receiverId);

    boolean deleteById(int notificationId);

    boolean deleteByReceiverId(String receiverId);

    boolean read(int notificationId);

    NotificationDto toDto(Notification notification);
}
