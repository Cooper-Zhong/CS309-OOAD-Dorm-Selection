package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "teachers")
//@PrimaryKeyJoinColumn(name = "teacher_id", referencedColumnName = "campus_id")
public class Teacher extends User {

    @NotNull
    @Column(name = "name")
    private String name;

}
