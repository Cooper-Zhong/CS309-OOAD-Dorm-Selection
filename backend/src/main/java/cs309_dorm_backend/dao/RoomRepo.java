package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;


import cs309_dorm_backend.domain.Room;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;

public interface RoomRepo extends JpaRepository<Room, Integer> {

    @Query(value = "select * from rooms where building_id = :buildingId and room_number = :roomNumber", nativeQuery = true)
    Room findOneRoom(int buildingId, int roomNumber);

    Room findByBuildingAndRoomNumber(@NotNull Building building, @NotNull int roomNumber);

    void deleteByRoomNumberAndBuilding(@NotNull int roomNumber, @NotNull Building building);
}
