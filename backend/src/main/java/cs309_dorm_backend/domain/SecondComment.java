package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "second_comments")
//@IdClass(SecondCommentId.class)
public class SecondComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "second_comment_id")
    private int secondCommentId;


//    @JsonIgnore
    @JsonAlias("parentCommentId")
    @JsonIdentityReference(alwaysAsId = true) //当序列化 SecondComment 实体时，只会包含 Comment 的 parentCommentId 属性
    //使用 parentCommentId 属性作为标识来识别 Comment 实体。
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id", nullable = false)
    private Comment parentComment;


    @JsonAlias("authorId")
    @JsonIdentityReference(alwaysAsId = true) //当序列化 SecondComment 实体时，只会包含 User 的 authorId 属性
    //使用 authorId 属性作为标识来识别 User 实体。
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    @ManyToOne(fetch = FetchType.LAZY) // a second comment can only belong to one user
    @JoinColumn(name = "author_id", referencedColumnName = "campus_id", nullable = false)
    private User author;

    @NotNull
    @Column(name = "content",nullable = false)
    private String content;

    @NotNull
    @Column(name = "time",nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp time;

}

//@Data
//class SecondCommentId implements java.io.Serializable {
//    private Comment parentComment;
//    private User author;
//    private Timestamp time;
//}
