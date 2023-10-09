package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Room {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private int floor_id;
    private int room_id;
    private String room_type;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getFloor_id() {
        return floor_id;
    }
    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }
    public int getRoom_id() {
        return room_id;
    }
    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
    public String getRoom_type() {
        return room_type;
    }
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}

