package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cs309_dorm_backend.api.BuildingRepos;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepos BuildingRepos;

    @Override
    public void deleteById(long id) {
        BuildingRepos.deleteById(id);
    }

    @Override
    public Building save(Building Building) {
        return null;
    }

    public List<Building> findAll() {
        return BuildingRepos.findAll();
    }
}
