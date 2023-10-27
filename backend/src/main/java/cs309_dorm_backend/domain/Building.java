package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


//building_id int primary key,
//        zone_name   varchar(30) references zones (name),
//        max_height  int not null

@Entity
@Setter
@Getter
@Table(name = "buildings")
public class Building {

    @Id
    @Column(name = "building_id")
    private int buildingId;

    @NotNull
    private int maxHeight;

    @ManyToOne(fetch = FetchType.LAZY) // a building belongs to a zone
    @JoinColumn(name = "zone_name", referencedColumnName = "name")
    private Zone zone;

    @OneToMany(mappedBy = "building") // a building can have many rooms
    private List<Room> rooms;


}

