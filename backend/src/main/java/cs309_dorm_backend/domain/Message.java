package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Reference;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp messageTime;

//    @OneToOne
//    @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id")
//    private User receiver;

//    @ManyToMany
//    @JoinTable(name = "message_receivers",
//            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "message_id"),
//            inverseJoinColumns = @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id"))
//    private Set<User> receivers;

    @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id")
    @OneToOne
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Message 实体时，只会包含 User 的 campusId 属性
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    private User receiver;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

}
