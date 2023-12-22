package cs309_dorm_backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    @Column(nullable = false)
    private String type; // comment, system, invitation

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Message 实体时，只会包含 User 的 campusId 属性
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    @JoinColumn(name = "receiver_id", referencedColumnName = "campus_id", foreignKey = @ForeignKey(name = "notification_users", value = ConstraintMode.CONSTRAINT))
    private User receiver;

    @Column(nullable = false)
    private boolean read;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp time;

    @Column(nullable = false)
    private String content; //json string

}
