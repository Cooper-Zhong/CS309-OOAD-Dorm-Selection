package cs309_dorm_backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Zone;

import java.util.List;

public interface ZoneService {
    public List<Zone> findAll();

    public void deleteById(String name);

    public Zone save(Zone zone);
}
