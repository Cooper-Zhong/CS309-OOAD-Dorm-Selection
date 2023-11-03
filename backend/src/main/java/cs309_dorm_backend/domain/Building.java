package cs309_dorm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;


//building_id int primary key,
//        zone_name   varchar(30) references zones (name),
//        max_height  int not null

@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "buildings")
public class Building {

    @Id
    @Column(name = "building_id")
    private int buildingId;

    @NotNull
    private int maxHeight;

    @ManyToOne // a building belongs to a zone
    @NotNull
    @JoinColumn(name = "zone_id", referencedColumnName = "zone_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    //将 zone 对象的 name 属性作为字段
    private Zone zone;

    @JsonIgnore
    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY) // a building can have many rooms
    private List<Room> rooms;
}

