package cs309_dorm_backend.service.room;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.RoomRepo;
import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.service.building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private BuildingService buildingService;

    @Override
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    public Room findOne(int buildingId, int roomNumber) {
        return roomRepo.findOneRoom(buildingId, roomNumber);
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Room> room = roomRepo.findById(id);
        if (room.isPresent()) {
            roomRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Room addOne(RoomDto roomDto) {
        int buildingId = roomDto.getBuildingId();
        int roomNumber = roomDto.getRoomNumber();
//        Building building = buildingService.findById(buildingId);
//        if (building == null) { // building does not exist
//            throw new MyException(404, "building " + buildingId + " does not exist");
//        }
        Room old = findOne(buildingId, roomNumber);
        if (old != null) { // room already exists
            throw new MyException(400, "room " + buildingId + "-" + roomNumber + " already exists");
        } else {
            return save(convertToRoom(roomDto));
        }
    }

    @Override
    public Room save(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public Room update(RoomDto roomDto) {
        int buildingId = roomDto.getBuildingId();
        int roomNumber = roomDto.getRoomNumber();
//        Building building = buildingService.findById(buildingId);
//        if (building == null) { // building does not exist
//            throw new MyException(404, "building " + buildingId + " does not exist");
//        }
        Room old = findOne(buildingId, roomNumber);
        if (old == null) { // room does not exist
            throw new MyException(404, "room " + buildingId + "-" + roomNumber + " does not exist");
        } else {
            Room room = convertToRoom(roomDto);
            room.setRoomId(old.getRoomId()); // maintain id
            return save(room);
        }
    }

    private Room convertToRoom(RoomDto roomDto) {
        Building building = buildingService.findById(roomDto.getBuildingId());
        if (building == null) {
            throw new MyException(404, "building " + roomDto.getBuildingId() + " does not exist");
        }
        return Room.builder().building(building)
                .roomNumber(roomDto.getRoomNumber())
                .roomType(roomDto.getRoomType())
                .floor(roomDto.getFloor())
                .gender(roomDto.getGender())
                .description(roomDto.getDescription())
                .build();
    }
}
