package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Selection {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private int team_id;
    private int room_id;
    private int slection_period_id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getTeam_id() {
        return team_id;
    }
    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
    public int getRoom_id() {
        return room_id;
    }
    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
    public int getSlection_period_id() {
        return slection_period_id;
    }
    public void setSlection_period_id(int slection_period_id) {
        this.slection_period_id = slection_period_id;
    }

}

