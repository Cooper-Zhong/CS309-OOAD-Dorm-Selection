package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "notification_recipients")
@IdClass(NotificationRecipientId.class)
public class NotificationRecipient {

    @Id
    @ManyToOne // can send one notification to many recipients
    @JoinColumn(name = "notification_id", referencedColumnName = "notification_id")
    private NotificationContent notificationContent;

    @Id
    @Column(name = "recipient_id", nullable = false)
    private int recipientId;

    @Column(name = "is_read")
    private boolean isRead;

    // Constructors, getters, and setters
}

@Data
class NotificationRecipientId implements Serializable {

    private Long notificationContent;
    private Long recipientId;

    // Constructors, getters, setters, and equals/hashCode methods
}
