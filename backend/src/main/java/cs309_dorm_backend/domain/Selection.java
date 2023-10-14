package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Time;

@Entity
public class Selection {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private int team_id;
    private int room_id;
    private Time selection_time;

    public int getSelection_period_id() {
        return selection_period_id;
    }

    public void setSelection_period_id(int selection_period_id) {
        this.selection_period_id = selection_period_id;
    }

    private int selection_period_id;

    public Time getSelection_time() {
        return selection_time;
    }

    public void setSelection_time(Time selection_time) {
        this.selection_time = selection_time;
    }


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


}

