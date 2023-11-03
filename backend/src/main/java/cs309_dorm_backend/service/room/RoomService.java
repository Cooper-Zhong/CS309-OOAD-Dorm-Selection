package cs309_dorm_backend.service.room;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room findOne(int buildingId, int roomNumber);

    boolean deleteById(int id);

    Room addOne(RoomDto roomDto);

    Room save(Room room);

    Room update(RoomDto roomDto);
}
