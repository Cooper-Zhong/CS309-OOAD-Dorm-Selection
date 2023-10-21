package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity // declare a class is an Entity class
@Setter
@Getter
@Table(name = "zones")
public class Zone {

    @Id // primary key
    private String name;

    @OneToMany(mappedBy = "zone") // a zone can have many buildings
    private List<Building> buildings;

}

