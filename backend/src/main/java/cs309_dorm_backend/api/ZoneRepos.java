package cs309_dorm_backend.api;
import cs309_dorm_backend.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ZoneRepos extends JpaRepository<Zone,Long>{
    public List<Zone> findAll();
}
