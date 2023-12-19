package cs309_dorm_backend.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.Timestamp;

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

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp time;

    @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id")
    @OneToOne
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Message 实体时，只会包含 User 的 campusId 属性
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    private User receiver;

    @JoinColumn(name = "sender_id", referencedColumnName = "campus_id")
    @OneToOne
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Message 实体时，只会包含 User 的 campusId 属性
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    private User sender;

    @Column(name = "read", nullable = false)
    private boolean read;

}
