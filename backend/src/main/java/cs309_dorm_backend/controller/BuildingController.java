package cs309_dorm_backend.controller;

import java.util.List;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.dto.BuildingDto;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.service.building.BuildingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/building")
@Api(tags = "Building Controller")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @AntiReptile
    @GetMapping("/findAll")
    public List<Building> findAll() {
        return buildingService.findAll();
    }

    @GetMapping("/findById/{buildingId}")
    public GlobalResponse findById(@PathVariable int buildingId) {
        Building building = buildingService.findById(buildingId);
        if (building == null) {
            return new GlobalResponse<>(1, "building not found", null);
        } else {
            return new GlobalResponse<>(0, "success", building);
        }
    }

    @DeleteMapping("/deleteById/{buildingId}")
    public GlobalResponse deleteById(@PathVariable int buildingId) {
        boolean result = buildingService.deleteById(buildingId);
        if (result) {
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "building not found", null);
        }
    }

    @PostMapping("/save")
    public GlobalResponse addOne(@RequestBody @Valid BuildingDto buildingDto, BindingResult result) {
        Building building = buildingService.addOne(buildingDto, result);
        if (building == null) {
            return new GlobalResponse<>(1, "building already exists", null);
        } else {
            return new GlobalResponse<>(0, "success", building);
        }
    }

    @PostMapping("/update")
    public GlobalResponse update(@RequestBody BuildingDto buildingDto, BindingResult result) {
        Building building = buildingService.update(buildingDto, result);
        if (building == null) {
            return new GlobalResponse<>(1, "building not found", null);
        } else {
            return new GlobalResponse<>(0, "success", building);
        }
    }
}
