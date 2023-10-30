package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.RoomRepo;
import cs309_dorm_backend.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

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
    public Room save(Room room) {
        return roomRepo.save(room);
    }
}
