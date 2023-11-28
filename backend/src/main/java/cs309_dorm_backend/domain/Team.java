package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
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


    @NonNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "creator_id", unique = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonAlias("creatorId")
    private Student creator;

    @OneToMany(mappedBy = "team")
    // only serialize id.
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentId")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Student> teamMembers;

    //    @JsonIgnore
    @ManyToMany// a team can favorite many rooms
    @JoinTable(
            name = "favorite_rooms", // 中间表的名字
            joinColumns = @JoinColumn(name = "team_id"), // 中间表的外键
            inverseJoinColumns = @JoinColumn(name = "room_id")) // 中间表的另一个外键
    private Set<Room> favoriteRooms;

}

