package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.dto.SelectDto;
import cs309_dorm_backend.service.room.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/findOne/{buildingId}/{roomNumber}")
    public GlobalResponse findOne(@PathVariable int buildingId, @PathVariable int roomNumber) {
        Room room = roomService.findOne(buildingId, roomNumber);
        if (room == null) {
            return new GlobalResponse<>(1, "room not found", null);
        } else {
            return new GlobalResponse<>(0, "success", room);
        }
    }

    @GetMapping("/findAssignedTeam/{roomId}")
    public GlobalResponse findAssignedTeam(@PathVariable int roomId) {
        Team team = roomService.findAssignedTeam(roomId);
        return new GlobalResponse<>(0, "success", team);
    }

    @DeleteMapping("/delete/{buildingId}/{roomNumber}")
    public GlobalResponse deleteById(@PathVariable int buildingId, @PathVariable int roomNumber) {
        boolean result = roomService.delete(buildingId, roomNumber);
        if (result) {
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "room not found", null);
        }
    }

    @PostMapping("/addOne")
    public GlobalResponse addOne(@RequestBody RoomDto roomDto) {
        Room room = roomService.addOne(roomDto);
        if (room == null) {
            return new GlobalResponse<>(1, "room already exists", null);
        } else {
            return new GlobalResponse<>(0, "success", room);
        }
    }

    @PostMapping("/update")
    public GlobalResponse update(@RequestBody RoomDto roomDto) {
        Room room = roomService.update(roomDto);
        if (room == null) {
            return new GlobalResponse<>(1, "room not found", null);
        } else {
            return new GlobalResponse<>(0, "success", room);
        }
    }


}

