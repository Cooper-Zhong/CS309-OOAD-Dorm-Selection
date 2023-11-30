package cs309_dorm_backend.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message_receivers")
@IdClass(MessageReceiverId.class)
public class MessageReceiver {

    // message_id + receiver_id 代表一条消息的接收者
    @Id
    @ManyToOne // can send one content to many recipients
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    private Message message;

    @Id
    @OneToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id")
    private User receiver;

    @Column(name = "is_read")
    private boolean isRead;

}

@Data
class MessageReceiverId implements Serializable {

    private Message message;
    private User receiver;

}
