package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.ZoneRepo;
import cs309_dorm_backend.domain.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepo zoneRepo;


    @Override
    public List<Zone> findAll() {
        return zoneRepo.findAll();
    }

    @Override
    public void deleteById(String name) {
        zoneRepo.deleteById(name);
    }

    @Override
    public Zone save(Zone zone) {
        return zoneRepo.save(zone);
    }
}

