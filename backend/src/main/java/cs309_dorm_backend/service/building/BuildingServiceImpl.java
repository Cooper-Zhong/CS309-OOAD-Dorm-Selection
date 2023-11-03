package cs309_dorm_backend.service.building;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.dto.BuildingDto;
import cs309_dorm_backend.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import cs309_dorm_backend.dao.BuildingRepo;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepo buildingRepo;

    @Autowired
    private ZoneService zoneService;

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

    @Override
    public Building addOne(@Valid BuildingDto buildingDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new MyException(400, result.getFieldError().getDefaultMessage());
        }
        int buildingId = buildingDto.getBuildingId();
        Building building1 = findById(buildingId);
        if (building1 == null) { // if the building does not exist
            return save(convertToBuilding(buildingDto));
        } else {
            throw new MyException(400, "building " + buildingId + " already exists");
        }
    }

    @Override
    public Building update(@Valid BuildingDto buildingDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new MyException(400, result.getFieldError().getDefaultMessage());
        }
        int buildingId = buildingDto.getBuildingId();
        Building building1 = findById(buildingId);
        if (building1 == null) { // if the building does not exist
            throw new MyException(404, "building " + buildingId + " does not exist");
        } else {
            return save(convertToBuilding(buildingDto));
        }
    }

    private Building convertToBuilding(BuildingDto buildingDto) {
        Zone temp = zoneService.findByName(buildingDto.getZoneName());
        if (temp == null) {
            throw new MyException(404, "zone " + buildingDto.getZoneName() + " does not exist");
        }
        return Building.builder()
                .buildingId(buildingDto.getBuildingId())
                .maxHeight(buildingDto.getMaxHeight())
                .zone(temp)
                .build();
    }
}
