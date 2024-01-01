package cs309_dorm_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "campus_id", length = 10)
    private String campusId;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments; // a user can have many first comments

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<SecondComment> secondComments; // a user can have many second comments

    public User(String campusId, String password, String role) {
        this.campusId = campusId;
        this.password = password;
        this.role = role;
    }
}


