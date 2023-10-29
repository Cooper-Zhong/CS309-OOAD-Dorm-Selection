package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepo extends JpaRepository<Building, Integer> {

}

