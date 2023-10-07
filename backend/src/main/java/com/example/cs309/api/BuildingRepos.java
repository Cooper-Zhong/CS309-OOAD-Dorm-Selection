package com.example.cs309.api;

import com.example.cs309.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepos extends JpaRepository<Building,Long>{
    public List<Building> findAll();
}

