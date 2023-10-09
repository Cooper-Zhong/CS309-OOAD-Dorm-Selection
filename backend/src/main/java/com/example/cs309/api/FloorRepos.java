package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Floor;
import java.util.List;

public interface FloorRepos extends JpaRepository<Floor,Long> {
    public List<Floor> findAll();
}
