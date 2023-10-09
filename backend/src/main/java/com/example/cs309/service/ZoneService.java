package backend.service;
import backend.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ZoneRepos extends JpaRepository<Zone,Long>{
    public List<Zone> findAll();
}
