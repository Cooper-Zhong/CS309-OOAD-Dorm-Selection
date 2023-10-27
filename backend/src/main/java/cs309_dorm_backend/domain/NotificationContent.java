package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;



@Entity
@Setter
@Getter
@Table(name = "notification_content")
public class NotificationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "time", nullable = false)
    private Timestamp time;
}

