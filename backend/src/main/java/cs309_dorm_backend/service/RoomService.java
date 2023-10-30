package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room findOne(int buildingId, int roomNumber);

    boolean deleteById(int id);

    Room save(Room building);
}
