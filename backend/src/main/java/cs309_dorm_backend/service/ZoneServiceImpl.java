package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.ZoneRepo;
import cs309_dorm_backend.domain.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepo zoneRepo;


    @Override
    public List<Zone> findAll() {
        return zoneRepo.findAll();
    }

    @Override
    public Zone findById(String name) {
        return zoneRepo.findById(name).orElse(null);
    }

    @Override
    public boolean deleteById(String name) {
        Optional<Zone> zoneOptional = zoneRepo.findById(name);
        if (zoneOptional.isPresent()) {
            zoneRepo.deleteById(name);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }

    @Override
    public Zone save(Zone zone) {
        return zoneRepo.save(zone);
    }
}

