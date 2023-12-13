package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms", uniqueConstraints = @UniqueConstraint(columnNames = {"building_id", "room_number"}))
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @NotNull
    @JsonAlias("buildingId")
//    @JsonIdentityReference(alwaysAsId = true) //当序列化 Room 实体时，只会包含 Building 的 buildingId 属性
    //使用 buildingId 属性作为标识来识别 Building 实体。
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "buildingId")
    @ManyToOne(fetch = FetchType.LAZY) // a room belongs to a building
    @OnDelete(action = OnDeleteAction.CASCADE) //当删除 building 时，删除该 building 下的所有 room
    @JoinColumn(name = "building_id", referencedColumnName = "building_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_room_building", value = ConstraintMode.CONSTRAINT))
    private Building building;

    @NotNull
    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @NotNull
    @Column(nullable = false)
    private int floor;

    @NotNull
    @Column(name = "room_type", nullable = false) // 1,2,3,4 for single, double, triple, quad
    private int roomType;

    @NotNull
    @Column(nullable = false)
    private String gender;

    private String description;


    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY) // a room can have many comments, or no comments
    private Set<Comment> comments;

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteRooms", fetch = FetchType.LAZY) // a room can be favorited by many teams
    private Set<Team> favoriteTeams;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    // a room can be assigned to one team
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team assignedTeam; // a room can only be assigned to one team
}

