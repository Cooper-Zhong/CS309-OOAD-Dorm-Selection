package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.Floor;
public interface FloorRepos extends JpaRepository<Floor,Long> {
    public List<Floor> findAll();
}
