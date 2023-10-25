package cs309_dorm_backend.domain;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity // declare a class is an Entity class
@Setter
@Getter
@Table(name = "zones")
@ApiModel(value = "Zone")
public class Zone {

    @Id // primary key
    private String name;

    @OneToMany(mappedBy = "zone") // a zone can have many buildings
    private List<Building> buildings;

}

