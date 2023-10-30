package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.service.BuildingService;
import cs309_dorm_backend.service.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
@Api(tags = "Room Controller")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/findAll")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @GetMapping("/findOne")
    public Room findOne(@RequestParam int buildingId, @RequestParam int roomNumber) {
        return roomService.findOne(buildingId, roomNumber);
    }

    @DeleteMapping("/deleteById/{roomId}")
    public boolean deleteById(@PathVariable int roomId) {
        return roomService.deleteById(roomId);
    }

    /**
     * add a new room
     *
     * @return //
     */
    @PostMapping("/save")
    public ResponseEntity<String> addOne(@RequestBody Room room) {
        int buildingId = room.getBuilding().getBuildingId();
        int roomNumber = room.getRoomNumber();
        Building building = buildingService.findById(buildingId);
        if (building == null) { // building does not exist
            return ResponseEntity.status(400).body("building " + buildingId + " does not exist");
        }
        Room old = roomService.findOne(buildingId, roomNumber);
        if (old != null) { // room already exists
            return ResponseEntity.status(400).body("room " + buildingId + "-" + roomNumber + " already exists");
        } else {
            room.setBuilding(building); // maintain referential integrity
            roomService.save(room);
            return ResponseEntity.ok("room " + buildingId + "-" + roomNumber + " added");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Room room) {
        int buildingId = room.getBuilding().getBuildingId();
        int roomNumber = room.getRoomNumber();
        Room old = roomService.findOne(buildingId, roomNumber);
        if (old == null) { // room does not exist
            return ResponseEntity.status(400).body("room " + buildingId + "-" + roomNumber + " does not exist");
        } else {
            room.setRoomId(old.getRoomId());
            roomService.save(room);
            return ResponseEntity.ok("room " + buildingId + "-" + roomNumber + " updated");
        }
    }
}

