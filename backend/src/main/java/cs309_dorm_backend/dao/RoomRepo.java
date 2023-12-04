package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;


import cs309_dorm_backend.domain.Room;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

public interface RoomRepo extends JpaRepository<Room, Integer> {

    @Query(value = "select * from rooms where building_id = :buildingId and room_number = :roomNumber", nativeQuery = true)
    Room findOneRoom(int buildingId, int roomNumber);

    Room findByBuildingAndRoomNumber(@NotNull Building building, @NotNull int roomNumber);

    void deleteByRoomNumberAndBuilding(@NotNull int roomNumber, @NotNull Building building);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT r FROM Room r WHERE r.roomId = :roomId")
    Room findRoomWithLock(@Param("roomId") int roomId);

    @Modifying
    @Query(value = "update rooms set team_id = :teamId where room_id = :roomId", nativeQuery = true)
    void updateRoomAssignedTeam(@Param("roomId") int roomId, @Param("teamId") int teamId);

    @Query(value = "select * from rooms where team_id = :teamId", nativeQuery = true)
    Room findSelectedRoom(@Param("teamId") int teamId);


}
