package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Zone;
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
    public Zone findByName(@PathVariable String name) {
        return zoneService.findByName(name);
    }

    @PostMapping("/save")
    public Zone addOne(@RequestBody Zone zone) {
        return zoneService.save(zone);
    }

    @PutMapping("/update")
    public Zone update(@RequestBody ZoneUpdateDto zoneUpdateDto) {
        return zoneService.update(zoneUpdateDto);
    }

    @DeleteMapping("/deleteByName/{name}")
    public boolean deleteByName(@PathVariable String name) {
        return zoneService.deleteByName(name);
    }
}