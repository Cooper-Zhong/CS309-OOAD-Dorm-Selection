package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Message;
import cs309_dorm_backend.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    @Query(value = "select * from notifications where receiver_id = ?1", nativeQuery = true)
    List<Notification> findByReceiverId(String receiverId);

    @Modifying
    @Query(value = "delete from notifications where receiver_id = ?1", nativeQuery = true)
    void deleteByReceiverId(String receiverId);

    void deleteByNotificationId(int notificationId);

}
