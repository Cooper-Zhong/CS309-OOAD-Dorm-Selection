package cs309_dorm_backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "campus_id")
    private int campusId;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private List<Comment> comments; // a user can have many first comments

    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private List<SecondComment> secondComments; // a user can have many second comments


}


