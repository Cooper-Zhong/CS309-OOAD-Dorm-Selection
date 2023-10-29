package cs309_dorm_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity // declare a class is an Entity class
@Setter
@Getter
@Table(name = "zones")
@ApiModel(value = "Zone")
public class Zone {

    @Id // primary key
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "zone",fetch = FetchType.LAZY) // a zone can have many buildings
    private List<Building> buildings;

}

