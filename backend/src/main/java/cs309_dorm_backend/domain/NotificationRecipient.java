package cs309_dorm_backend.domain;

import javax.persistence.*;

import cs309_dorm_backend.domain.User;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "notification_recipients")
@IdClass(NotificationRecipientId.class)
public class NotificationRecipient {
    // id, recipient_id 代表了一个用户收到的一个通知，通知的id为notification_id

    @Id
    @ManyToOne // can send one content to many recipients
    @JoinColumn(name = "notification_id", referencedColumnName = "notification_id")
    private NotificationContent notificationContent;

    @Id
    @OneToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "campus_id")
    private User recipient;

    @Column(name = "is_read")
    private boolean isRead;

    // Constructors, getters, and setters
}

@Data
class NotificationRecipientId implements Serializable {

    private NotificationContent notificationContent;
    private User recipient;

    // Constructors, getters, setters, and equals/hashCode methods
}
