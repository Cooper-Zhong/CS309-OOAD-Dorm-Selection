package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;

    @NonNull
    @NotBlank
    @Column(name = "team_name", unique = true)
    private String teamName;


    private String teamInfo;

    @NotNull
    @Column(nullable = false)
    private String gender;


//    @NonNull
//    @NotBlank
//    @OneToOne
//    @JoinColumn(name = "creator_id", unique = true)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonAlias("creatorId")
//    private Student creator;

    @NonNull
    @NotBlank
    @Column(name = "creator_id", unique = true)
    private String creatorId;

//    @Formula("(SELECT s.degree FROM students s WHERE s.student_id = creator_id)")
//    private String degree;

    @OneToMany(mappedBy = "team")
    // only serialize id.
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
//    @JsonIdentityReference(alwaysAsId = true)
    private Set<Student> teamMembers;

//    @JsonIgnore
    @ManyToMany// a team can favorite many rooms
    @JoinTable(
            name = "favorite_rooms", // 中间表的名字
            joinColumns = @JoinColumn(name = "team_id"), // 中间表的外键
            inverseJoinColumns = @JoinColumn(name = "room_id"
                    , foreignKey = @ForeignKey(name = "fk_favorite_room", value = ConstraintMode.NO_CONSTRAINT))
    ) // 中间表的另一个外键
    private Set<Room> favoriteRooms;
}

