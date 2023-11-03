package cs309_dorm_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity // declare a class is an Entity class
@Setter
@Getter
@Table(name = "zones")
@ApiModel(value = "Zone")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zone_id")
    private int zoneId;

    @Column(unique = true, nullable = false)
    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY) // a zone can have many buildings
    private List<Building> buildings;

}

