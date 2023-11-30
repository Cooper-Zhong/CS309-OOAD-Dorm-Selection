package cs309_dorm_backend.domain;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

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
    @Column(name = "message_id")
    private int messageId;

    @NotNull
    private boolean isRead;
    private String messageContent;
    private String messageTime;
    private String MessageOwnerId;
    private int messageSender;

    public Message(int messageId, String messageContent, String messageTime, int messageOwnerId, boolean isRead,int messageSender) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.messageTime = messageTime;
        this.MessageOwnerId = String.valueOf(messageOwnerId);
        this.isRead = isRead;
        this.messageSender = messageSender;
    }

}
