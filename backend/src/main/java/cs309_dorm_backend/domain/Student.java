package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
//@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "campus_id")
public class Student extends User {
    // student_id refers to campus_id in users table

    @NotNull
    private String name;

    @NotNull
    private String gender;

    @NotNull
    private String degree; // b,m, d for bachelor, master, doctor

    @NotNull
    private String major; //前端限制好major的名字，要统一

    private String info;

    @ManyToOne(fetch = FetchType.LAZY) // a student can only belong to one team
    @JoinColumn(name = "team_id")
    private Team team; // a student's team, can be null

}

