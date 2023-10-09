package cs309_dorm_backend.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

//- faculty_id (Primary Key)
//        - user_id (Foreign Key referencing `User`)
//        - name varchar
@Entity
public class Faculty {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long faculty_id;
    @NotNull // null then cannot be executed
    private long user_id;
    private String name;
    public long getFaculty_id() {
        return faculty_id;
    }
    public void setFaculty_id(long faculty_id) {
        this.faculty_id = faculty_id;
    }
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
