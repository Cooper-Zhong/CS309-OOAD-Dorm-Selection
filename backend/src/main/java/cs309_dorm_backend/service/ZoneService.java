package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Zone;

import java.util.List;

public interface ZoneService {

    List<Zone> findAll();

    Zone findById(String name);

    boolean deleteById(String name);

    Zone save(Zone zone);
}
