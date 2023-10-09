package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

@Entity // declare a class is an Entity class
public class Zone {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private String zone_name;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }
}

