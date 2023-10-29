package cs309_dorm_backend.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs309_dorm_backend.domain.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    private int teacherId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // 使用 @MapsId 注解，以便将主键的值映射到外键列
    @JoinColumn(name = "teacher_id", referencedColumnName = "campus_id")
    private User user;

    @Column(name = "name")
    private String name;

}
