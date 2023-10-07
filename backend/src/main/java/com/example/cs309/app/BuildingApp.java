package com.example.cs309.app;

import com.example.cs309.domain.Building;
import com.example.cs309.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //类控制器
@RequestMapping("/bui") //请求映射
public class BuildingApp {
    @Autowired
    private BuildingService buildingService;

    @GetMapping("/record")
    public List<Building> findAll() {
        System.out.println(buildingService.getClass().getName());
        return buildingService.findAll();
    }

    @PostMapping("/record")
    public Building addOne(Building building) {
        return buildingService.save(building);
    }

    //use requestparam to update a line
    @PutMapping("/record")
    public Building update(@RequestParam long id,
                           @RequestParam int hight,
                           @RequestParam int zone_id) {
        Building building = new Building();
        building.setId(id);
        building.setHeight(hight);
        building.setZone_id(zone_id);
        return buildingService.save(building);
    }

    @DeleteMapping("record/{id}")
    public void deleteOne(@PathVariable long id) {
        buildingService.deleteById(id);
    }

}
