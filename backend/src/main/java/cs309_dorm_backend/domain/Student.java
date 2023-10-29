package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    // student_id refers to campus_id in users table
    @Id
    @Column(name = "student_id")
    private int studentId;

    @JsonIgnore //被序列化成JSON时，user字段将被忽略
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // 使用 @MapsId 注解，以便将主键的值映射到外键列
    @JoinColumn(name = "student_id", referencedColumnName = "campus_id")
    private User user;

    private String name;

    private String gender;

    private String degree; // b,m, d for bachelor, master, doctor

    private String major; //前端限制好major的名字，要统一

    private String info;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // a student can only belong to one team
    @JoinColumn(name = "team_id")
    private Team team; // a student's team, can be null

}

