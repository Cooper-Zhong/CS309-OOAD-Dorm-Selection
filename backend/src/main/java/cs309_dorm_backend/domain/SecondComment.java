package cs309_dorm_backend.domain;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "second_comments")
public class SecondComment {
    @Id
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private Timestamp time;

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
