package cs309_dorm_backend.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

//    id INT PRIMARY KEY AUTO_INCREMENT,
//            student_id INT NOT NULL,
//            room_id INT NOT NULL, 必须依附于一个房间
//            comment_text TEXT NOT NULL,
//            parent_comment_id INT,
//            FOREIGN KEY (student_id) REFERENCES Student(student_id),
//            FOREIGN KEY (room_id) REFERENCES Room(id),
//            FOREIGN KEY (parent_comment_id) REFERENCES Comment(id)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long student_id;
    private long room_id;
    private String comment_text;

    private long parent_comment_id;

    public void setId(long id) {
        this.id = id;
    }
    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public void setParent_comment_id(long parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public long getId() {
        return id;
    }

    public long getStudent_id() {
        return student_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public long getParent_comment_id() {
        return parent_comment_id;
    }


}
