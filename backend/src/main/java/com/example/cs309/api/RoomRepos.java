package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Room;
import java.util.List;

public interface RoomRepos extends JpaRepository<Room,Long> {
    public List<Room> findAll();
}
