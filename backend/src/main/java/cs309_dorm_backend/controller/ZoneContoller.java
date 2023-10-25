package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.service.ZoneService;
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

    @GetMapping("/findById/{name}")
    public Zone findById(@PathVariable String name) {
        return zoneService.findById(name);
    }

    @PostMapping("/save")
    public ResponseEntity<String> addOne(@RequestBody Zone zone) {
        Zone zone1 = zoneService.findById(zone.getName());
        String name = zone.getName();
        if (zone1 == null) { // if the zone does not exist
            zoneService.save(zone);
            return ResponseEntity.ok("zone " + name + " added");
        } else {
            return ResponseEntity.status(400).body(name + " already exists");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Map<String, String> map) {
        String oldName = map.get("oldName");
        Zone zone = zoneService.findById(oldName);
        if (zone == null) {
            return ResponseEntity.status(404).body(oldName + " does not exist");
        } else {
            String newName = map.get("newName");
            zone.setName(newName);
            zoneService.save(zone);
            return ResponseEntity.ok("zone " + oldName + " updated to " + newName);
        }
    }

    @DeleteMapping("/deleteById/{name}")
    public boolean deleteById(@PathVariable String name) {
        return zoneService.deleteById(name);
    }
}