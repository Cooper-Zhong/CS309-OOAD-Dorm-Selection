package cs309_dorm_backend.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private Student creator;

    @OneToMany(mappedBy = "team")
    private List<Student> teamMembers;

    @ManyToMany// a team can favorite many rooms
    @JoinTable(
            name = "favorite_rooms", // 中间表的名字
            joinColumns = @JoinColumn(name = "team_id"), // 中间表的外键
            inverseJoinColumns = @JoinColumn(name = "room_id")) // 中间表的另一个外键
    private List<Room> favoriteRooms;

}

