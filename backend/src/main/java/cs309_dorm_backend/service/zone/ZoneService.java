package cs309_dorm_backend.service.zone;

import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.dto.ZoneUpdateDto;

import java.util.List;

public interface ZoneService {

    List<Zone> findAll();

    Zone findByName(String name);

    boolean deleteByName(String name);

    Zone save(Zone zone);

    Zone update(ZoneUpdateDto zoneUpdateDto);
}
