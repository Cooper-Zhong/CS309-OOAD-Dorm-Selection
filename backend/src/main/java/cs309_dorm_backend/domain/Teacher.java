package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private User user;

    @Column(name = "name")
    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
