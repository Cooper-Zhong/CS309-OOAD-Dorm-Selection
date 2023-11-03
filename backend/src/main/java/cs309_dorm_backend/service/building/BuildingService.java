package cs309_dorm_backend.service.building;

import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.dto.BuildingDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findById(int id);

    boolean deleteById(int id);

    Building save(Building building);

    Building addOne(BuildingDto buildingDto, BindingResult result);

    Building update(BuildingDto buildingDto, BindingResult result);
}
