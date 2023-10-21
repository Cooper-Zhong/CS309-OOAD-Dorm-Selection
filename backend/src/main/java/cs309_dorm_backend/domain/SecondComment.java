package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "second_comments")
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
