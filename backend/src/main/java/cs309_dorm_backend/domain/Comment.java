package cs309_dorm_backend.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @ManyToOne(fetch = FetchType.LAZY) // a comment can only belong to one user
    @JoinColumn(name = "author_id", referencedColumnName = "campus_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY) // a comment can only belong to one room
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room room;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private Timestamp time;

    @OneToMany(mappedBy = "parentComment",fetch = FetchType.LAZY) // a comment can have many second comments, or no second comments
    private List<SecondComment> secondComments;

}
