package cs309_dorm_backend.service.zone;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.ZoneRepo;
import cs309_dorm_backend.domain.Zone;
import cs309_dorm_backend.dto.ZoneUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Zone findByName(String name) {
        return zoneRepo.findByName(name);
    }

    @Override
    public boolean deleteByName(String name) {
        Zone zone = zoneRepo.findByName(name);
        if (zone != null) {
            zoneRepo.deleteById(zone.getZoneId());
            return true; // 删除成功
        } else {
            throw new MyException(404, "zone " + name + " does not exist");
        }
    }

    @Override
    public Zone save(Zone zone) {
        String name = zone.getName();
        Zone zone1 = findByName(name);
        if (zone1 == null) { // if the zone does not exist
            return zoneRepo.save(zone);
        } else {
            throw new MyException(400, "zone " + name + " already exists");
        }
    }

    @Override
    public Zone update(ZoneUpdateDto zoneUpdateDto) {
        String oldName = zoneUpdateDto.getOldName();
        Zone zone = zoneRepo.findByName(oldName);
        if (zone == null) {
            throw new MyException(404, "zone " + oldName + " does not exist");
        } else {
            String newName = zoneUpdateDto.getNewName();
            zone.setName(newName);
            return zoneRepo.save(zone);
        }
    }
}

