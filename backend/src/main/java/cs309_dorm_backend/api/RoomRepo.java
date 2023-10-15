package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.Room;

public interface RoomRepo extends JpaRepository<Room,Long> {
    public List<Room> findAll();
}
