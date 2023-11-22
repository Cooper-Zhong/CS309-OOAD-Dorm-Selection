package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.ZoneUpdateDto;
import cs309_dorm_backend.service.zone.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //类控制器
@RequestMapping("/zone") //请求映射
public class ZoneContoller {
    @Autowired
    private ZoneService zoneService;

    @GetMapping("/findAll")
    public List<Zone> findAll() {
        return zoneService.findAll();
    }

    @GetMapping("/findByName/{name}")
    public GlobalResponse<Zone> findByName(@PathVariable String name) {
        Zone zone = zoneService.findByName(name);
        if (zone == null) {
            return new GlobalResponse<>(1, "zone not found", null);
        } else {
            return new GlobalResponse<>(0, "success", zone);
        }
    }

    @PostMapping("/save")
    public GlobalResponse<Zone> addOne(@RequestBody Zone zone) {
        Zone zone1 = zoneService.save(zone);
        if (zone1 == null) {
            return new GlobalResponse<>(1, "zone already exists", null);
        } else {
            return new GlobalResponse<>(0, "success", zone1);
        }
    }

    @PutMapping("/update")
    public GlobalResponse update(@RequestBody ZoneUpdateDto zoneUpdateDto) {
        Zone zone = zoneService.update(zoneUpdateDto);
        if (zone == null) {
            return new GlobalResponse<>(1, "zone not found", null);
        } else {
            return new GlobalResponse<>(0, "success", zone);
        }
    }

    @DeleteMapping("/deleteByName/{name}")
    public GlobalResponse deleteByName(@PathVariable String name) {
        boolean result = zoneService.deleteByName(name);
        if (result) {
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "zone not found", null);
        }
    }
}