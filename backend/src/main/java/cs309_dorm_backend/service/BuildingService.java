package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Building;

import java.util.List;

public interface BuildingService {
    public List<Building> findAll();
    public void deleteById(long id);

    public Building save(Building building);
}
