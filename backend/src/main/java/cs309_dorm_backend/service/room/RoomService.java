package cs309_dorm_backend.service.room;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.RoomDto;
import cs309_dorm_backend.dto.SelectDto;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room findOne(int buildingId, int roomNumber);

    Room findById(int id);

    boolean delete(int buildingId, int roomNumber);

    Room addOne(RoomDto roomDto);

    Room save(Room room);

    Room update(RoomDto roomDto);

    Room selectRoom(SelectDto selectDto);

    Room unselectRoom(SelectDto selectDto);

    Room findSelectedRoom(int teamId);

    Team findAssignedTeam(int roomId);



}
