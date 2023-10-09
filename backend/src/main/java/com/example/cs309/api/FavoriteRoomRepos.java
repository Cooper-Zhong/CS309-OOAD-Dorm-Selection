package backend.api;
import backend.domain.FavoriteRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FavoriteRoomRepos extends JpaRepository<FavoriteRoom,Long>{
    public List<FavoriteRoom> findAll();
}
