package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // a room belongs to a building
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    private Building building;

    @NotNull
    @Column(name = "room_number")
    private int roomNumber;

    @NotNull
    private int floor;

    @NotNull
    @Column(name = "room_type") // 1,2,3,4 for single, double, triple, quad
    private int roomType;

    @NotNull
    private String gender;

    private String description;

    @OneToMany(mappedBy = "room") // a room can have many comments, or no comments
    private List<Comment> comments;

    @ManyToMany(mappedBy = "favoriteRooms") // a room can be favorited by many teams
    private List<Team> favoriteTeams;

    @OneToOne // a room can be assigned to one team
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team assignedTeam; // a room can only be assigned to one team


}

