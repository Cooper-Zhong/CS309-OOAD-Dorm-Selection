package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import cs309_dorm_backend.dao.BuildingRepo;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepo buildingRepo;

    @Override
    public List<Building> findAll() {
        return buildingRepo.findAll();
    }

    @Override
    public Building findById(int id) {
        return buildingRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Building> building = buildingRepo.findById(id);
        if (building.isPresent()) {
            buildingRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Building save(Building building) {
        return buildingRepo.save(building);
    }
}
