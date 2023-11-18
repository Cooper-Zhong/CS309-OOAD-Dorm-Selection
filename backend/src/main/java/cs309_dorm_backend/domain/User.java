package cs309_dorm_backend.domain;

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
    @Column(name = "campus_id")
    private int campusId;

    @NotNull
    @Column(name = "password",nullable = false)
    private String password;

    @NotNull
    @Column(name = "role",nullable = false)
    private String role;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments; // a user can have many first comments

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<SecondComment> secondComments; // a user can have many second comments

    public User(int campusId, String password, String role) {
        this.campusId = campusId;
        this.password = password;
        this.role = role;
    }


}


