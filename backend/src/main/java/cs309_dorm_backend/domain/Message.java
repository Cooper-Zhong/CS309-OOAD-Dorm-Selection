package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @Column(name = "message_title", nullable = false)
    private String messageTitle;

    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @Column(name = "message_time", nullable = false)
    private Timestamp messageTime;

}
