package com.example.cs309.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Floor {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private int building_id;
    private int floor_number;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getBuilding_id() {
        return building_id;
    }
    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }
    public int getFloor_number() {
        return floor_number;
    }
    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

