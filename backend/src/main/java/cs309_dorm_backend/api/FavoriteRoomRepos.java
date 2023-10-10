package cs309_dorm_backend.api;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.FavoriteRoom;
public interface FavoriteRoomRepos extends JpaRepository<FavoriteRoom,Long>{
    public List<FavoriteRoom> findAll();
}