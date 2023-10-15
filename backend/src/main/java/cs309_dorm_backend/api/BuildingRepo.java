package cs309_dorm_backend.api;

import cs309_dorm_backend.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepo extends JpaRepository<Building,Long>{
    public List<Building> findAll();
}

