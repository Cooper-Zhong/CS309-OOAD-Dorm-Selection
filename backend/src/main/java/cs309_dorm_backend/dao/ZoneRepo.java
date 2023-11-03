package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepo extends JpaRepository<Zone, Integer> {

    Zone findByName(String name);

}
