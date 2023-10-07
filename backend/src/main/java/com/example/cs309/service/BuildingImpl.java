package com.example.cs309.service;

import com.example.cs309.domain.Building;
import com.example.cs309.api.BuildingRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BuildingImpl implements BuildingService {
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

    public List<Building> findAll(){
        return BuildingRepos.findAll();
    }
}
