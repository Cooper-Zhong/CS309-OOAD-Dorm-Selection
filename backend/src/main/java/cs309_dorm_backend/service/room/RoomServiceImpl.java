package cs309_dorm_backend.service.room;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.RoomRepo;
import cs309_dorm_backend.domain.Building;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.dto.SelectDto;
import cs309_dorm_backend.service.building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private BuildingService buildingService;

    @Override
    @Cacheable(value = "rooms")
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    @Cacheable(value = "rooms", key = "#room.roomId")
    public Room findOne(int buildingId, int roomNumber) {
        Room room = roomRepo.findOneRoom(buildingId, roomNumber);
        return room;
    }

    @Override
    @Transactional
    @Cacheable(value = "rooms", key = "#id")
    public Room findById(int id) {
        return roomRepo.findById(id).orElse(null);
    }

    @Override
    @CacheEvict(value = "rooms", key = "#room.roomId")
    public boolean delete(int buildingId, int roomNumber) {
        Room room = findOne(buildingId, roomNumber);
        if (room != null) { // room exists
            roomRepo.delete(room);
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
    @CacheEvict(value = "rooms", key = "#room.roomId")
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

    @Override
    @Transactional
    public Room selectRoom(SelectDto selectDto) {
        int RoomId = selectDto.getRoomId();
        int teamId = selectDto.getTeamId();
        Room temp = roomRepo.findSelectedRoom(teamId);
        if (temp != null) { // team already selected a room
            throw new MyException(5, "team " + teamId + " already selected room " + temp.getRoomId());
        }

        Room room = roomRepo.findRoomWithLock(RoomId);
        if (room == null) { // room does not exist
            throw new MyException(404, "room " + RoomId + " does not exist");
        }
        if (room.getSelectedTeam() != null) { // room already assigned
            throw new MyException(5, "room " + RoomId + " already assigned to other team");
        }
        roomRepo.updateRoomAssignedTeam(RoomId, teamId);
        return room;
    }

    @Override
    @Transactional
    public Room unselectRoom(SelectDto selectDto) {
        int RoomId = selectDto.getRoomId();
        int teamId = selectDto.getTeamId();

        Room room = roomRepo.findRoomWithLock(RoomId);
        if (room == null) { // room does not exist
            throw new MyException(404, "room " + RoomId + " does not exist");
        }
        if (room.getSelectedTeam() == null) { // room not assigned
            throw new MyException(5, "You didn't select this room: " + RoomId);
        }
        if (room.getSelectedTeam().getTeamId() != teamId) { // room not unassigned to this team
            throw new MyException(400, "room " + RoomId + " not unassigned to team " + teamId);
        }
        roomRepo.updateRoomAssignedTeamToNull(teamId);
        return room;
    }

    @Override
    public Room findSelectedRoom(int teamId) {
        return roomRepo.findSelectedRoom(teamId);
    }

    @Override
    public Team findAssignedTeam(int roomId) {
        Room room = roomRepo.findById(roomId).orElse(null);
        if (room == null) {
            throw new MyException(404, "room " + roomId + " does not exist");
        }
        return room.getSelectedTeam();
    }

    @Override
    public void swapRoom(int roomId1, int roomId2) {
        roomRepo.swapRoom(roomId1, roomId2);
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
