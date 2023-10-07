package com.example.cs309.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

//- id (Primary Key)
//        - student_id (Foreign Key referencing `Student`)
//        - room_id (Foreign Key referencing `Room`)
@Entity
public class FavoriteRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long student_id;
    private long room_id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getStudent_id() {
        return student_id;
    }
    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }
    public long getRoom_id() {
        return room_id;
    }
    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

}
