package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.service.building.BuildingService;
import cs309_dorm_backend.service.room.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Savepoint;
import java.util.List;

@RestController
@RequestMapping("/room")
@Api(tags = "Room Controller")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/findAll")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @GetMapping("/findOne")
    public Room findOne(@RequestParam("buildingId") int buildingId, @RequestParam("roomNumber") int roomNumber) {
        return roomService.findOne(buildingId, roomNumber);
    }

    @DeleteMapping("/deleteById/{roomId}")
    public boolean deleteById(@PathVariable int roomId) {
        return roomService.deleteById(roomId);
    }

    @PostMapping("/addOne")
    public Room addOne(@RequestBody RoomDto roomDto) {
        return roomService.addOne(roomDto);
    }

    @PutMapping("/update")
    public Room update(@RequestBody RoomDto roomDto) {
        return roomService.update(roomDto);
    }
}

