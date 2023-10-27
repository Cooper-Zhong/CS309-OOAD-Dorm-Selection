package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "second_comments")
@IdClass(SecondCommentId.class)
public class SecondComment {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id")
    private Comment parentComment;

    @Id
    @ManyToOne(fetch = FetchType.LAZY) // a second comment can only belong to one user
    @JoinColumn(name = "author_id", referencedColumnName = "campus_id")
    private User author;

    @NotNull
    @Column(name = "content")
    private String content;

    @Id
    @Column(name = "time")
    private Timestamp time;

}

@Data
class SecondCommentId implements java.io.Serializable {
    private Comment parentComment;
    private User author;
    private Timestamp time;
}
