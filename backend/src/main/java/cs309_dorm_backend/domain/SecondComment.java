package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE) //当删除 comment 时，删除该 comment 下的所有 second comment
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_second_comment_comment", value = ConstraintMode.CONSTRAINT))
    private Comment parentComment;


    @JsonAlias("authorId")
    @JsonIdentityReference(alwaysAsId = true) //当序列化 SecondComment 实体时，只会包含 User 的 authorId 属性
    //使用 authorId 属性作为标识来识别 User 实体。
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "campusId")
    @ManyToOne(fetch = FetchType.EAGER) // a second comment can only belong to one user
    @OnDelete(action = OnDeleteAction.CASCADE) //当删除 user 时，删除该 user 下的所有 second comment
    @JoinColumn(name = "author_id", referencedColumnName = "campus_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_second_comment_user", value = ConstraintMode.CONSTRAINT))
    private User author;

    // 加一列author_name，用于前端显示
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp time;

}

//@Data
//class SecondCommentId implements java.io.Serializable {
//    private Comment parentComment;
//    private User author;
//    private Timestamp time;
//}
