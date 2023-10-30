package cs309_dorm_backend.controller;

import java.util.List;

import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.service.BuildingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/building")
@Api(tags = "Building Controller")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/findAll")
    public List<Building> findAll() {
        return buildingService.findAll();
    }

    @GetMapping("/findById/{buildingId}")
    public Building findById(@PathVariable int buildingId) {
        return buildingService.findById(buildingId);
    }

    @DeleteMapping("/deleteById/{buildingId}")
    public boolean deleteById(@PathVariable int buildingId) {
        return buildingService.deleteById(buildingId);
    }

    /**
     * add a new building
     *
     * @param building
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<String> addOne(@RequestBody Building building) {
        int buildingId = building.getBuildingId();
        Building building1 = buildingService.findById(buildingId);
        if (building1 == null) { // if the building does not exist
            try {
                buildingService.save(building);
                return ResponseEntity.ok("building " + buildingId + " added");
            } catch (Exception e) {
                return ResponseEntity.status(400).body("invalid building json/zone not exist/..., failed");
            }
        } else {
            return ResponseEntity.status(400).body("building " + buildingId + " already exists");
        }

    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Building building) {
        int buildingId = building.getBuildingId();
        Building old = buildingService.findById(buildingId);
        if (old == null) {
            return ResponseEntity.status(404).body("buildingId "+buildingId + " does not exist");
        } else {
            buildingService.deleteById(buildingId);
            buildingService.save(building);
            return ResponseEntity.ok("building " + buildingId + " updated");
        }
    }


}
