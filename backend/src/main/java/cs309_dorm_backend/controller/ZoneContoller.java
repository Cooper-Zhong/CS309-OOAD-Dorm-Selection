package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.ZoneUpdateDto;
import cs309_dorm_backend.service.zone.ZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //类控制器
@RequestMapping("/zone") //请求映射
@Slf4j
public class ZoneContoller {
    @Autowired
    private ZoneService zoneService;

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

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
            log.info("Zone {} saved", zone.getName());
            return new GlobalResponse<>(0, "success", zone1);
        }
    }

    @PostMapping("/update")
    public GlobalResponse update(@RequestBody ZoneUpdateDto zoneUpdateDto) {
        Zone zone = zoneService.update(zoneUpdateDto);
        if (zone == null) {
            return new GlobalResponse<>(1, "zone not found", null);
        } else {
            log.info("Zone {} updated", zone.getName());
            return new GlobalResponse<>(0, "success", zone);
        }
    }

    @DeleteMapping("/deleteByName/{name}")
    public GlobalResponse deleteByName(@PathVariable String name) {
        boolean result = zoneService.deleteByName(name);
        if (result) {
            log.info("Zone {} deleted", name);
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "zone not found", null);
        }
    }
}