package cs309_dorm_backend.controller;

import java.util.List;

import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.dto.BuildingDto;
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

    @PostMapping("/save")
    public Building addOne(@RequestBody @Valid BuildingDto buildingDto, BindingResult result) {
        return buildingService.addOne(buildingDto, result);
    }

    @PutMapping("/update")
    public Building update(@RequestBody BuildingDto buildingDto, BindingResult result) {
        return buildingService.update(buildingDto, result);
    }


}
