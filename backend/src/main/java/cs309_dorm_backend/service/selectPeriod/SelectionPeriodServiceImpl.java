package cs309_dorm_backend.service.selectPeriod;

import cs309_dorm_backend.dao.SelectionPeriodRepo;
import cs309_dorm_backend.domain.SelectionPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectionPeriodServiceImpl implements SelectionPeriodService {

    @Autowired
    private SelectionPeriodRepo selectionPeriodRepo;

    @Override
    public SelectionPeriod update(SelectionPeriod selectionPeriod) {
        int periodId = selectionPeriod.getPeriodId();
        SelectionPeriod old = selectionPeriodRepo.findById(periodId).orElse(null);
        if (old == null) {
            return null;
        }
        old.setStartTime(selectionPeriod.getStartTime());
        old.setEndTime(selectionPeriod.getEndTime());
        return selectionPeriodRepo.save(old);
    }

    @Override
    public SelectionPeriod findById(Integer periodId) {
        return selectionPeriodRepo.findById(periodId).orElse(null);
    }

    @Override
    public List<SelectionPeriod> findAll() {
        return selectionPeriodRepo.findAll();
    }
}
