package com.example.cs309.service;

import com.example.cs309.domain.Building;

import java.util.List;

public interface BuildingService {
    public List<Building> findAll();
    public void deleteById(long id);

    public Building save(Building building);
}
