package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.service.room.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/findOne/{buildingId}/{roomNumber}")
    public GlobalResponse findOne(@PathVariable int buildingId, @PathVariable int roomNumber) {
        Room room = roomService.findOne(buildingId, roomNumber);
        if (room == null) {
            return new GlobalResponse<>(1, "room not found", null);
        } else {
            return new GlobalResponse<>(0, "success", room);
        }
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

    @PutMapping("/update")
    public GlobalResponse update(@RequestBody RoomDto roomDto) {
        Room room = roomService.update(roomDto);
        if (room == null) {
            return new GlobalResponse<>(1, "room not found", null);
        } else {
            return new GlobalResponse<>(0, "success", room);
        }
    }
}

