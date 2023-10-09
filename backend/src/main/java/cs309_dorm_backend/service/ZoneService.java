package cs309_dorm_backend.service;
import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Zone;
import java.util.List;
public interface ZoneService extends JpaRepository<Zone,Long>{
    public List<Zone> findAll();
}
