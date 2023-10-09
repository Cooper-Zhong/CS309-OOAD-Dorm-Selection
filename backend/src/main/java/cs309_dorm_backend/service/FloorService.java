package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Floor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FloorService {
    List<Floor> findAll();

    void deleteById(long id);

    Floor save(Floor floor);

    void update(Floor floor);

}
