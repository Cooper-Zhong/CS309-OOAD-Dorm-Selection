package cs309_dorm_backend.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @JsonAlias("authorId")
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Comment 实体时，只会包含 User 的 authorId 属性
    //使用 authorId 属性作为标识来识别 User 实体。
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    @ManyToOne(fetch = FetchType.LAZY) // a comment can only belong to one user
    @JoinColumn(name = "author_id", referencedColumnName = "campus_id", nullable = false)
    private User author;

    @JsonAlias("roomId")
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Comment 实体时，只会包含 Room 的 roomId 属性
    //使用 roomId 属性作为标识来识别 Room 实体。
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomId")
    @ManyToOne(fetch = FetchType.LAZY) // a comment can only belong to one room
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp time;

    @OneToMany(mappedBy = "parentComment",fetch = FetchType.LAZY) // a comment can have many second comments, or no second comments
    private Set<SecondComment> secondComments;

}
