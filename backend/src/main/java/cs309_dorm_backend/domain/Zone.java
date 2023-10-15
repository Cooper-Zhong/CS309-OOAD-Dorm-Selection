package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // declare a class is an Entity class
@Table(name = "zones")
public class Zone {

    @Id // primary key
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String zone_name) {
        this.name = zone_name;
    }
}

