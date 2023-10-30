package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import cs309_dorm_backend.domain.Room;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepo extends JpaRepository<Room, Integer> {

    @Query(value = "select * from rooms where building_id = :buildingId and room_number = :roomNumber", nativeQuery = true)
    Room findOneRoom(int buildingId, int roomNumber);
}
