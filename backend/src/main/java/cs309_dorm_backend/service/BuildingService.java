package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Building;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findById(int id);

    boolean deleteById(int id);

    Building save(Building building);
}
