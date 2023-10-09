package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

//id INT PRIMARY KEY,
//        zone_id INT NOT NULL,
//        height INT NOT NULL,
//        FOREIGN KEY (zone_id) REFERENCES Zone(id)
@Entity // declare a class is an Entity class
public class Building {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;

    @NotNull // null then cannot be executed
    private int height;
    private long zone_id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getZone_id() {
        return zone_id;
    }

    public void setZone_id(long zone_id) {
        this.zone_id = zone_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}

