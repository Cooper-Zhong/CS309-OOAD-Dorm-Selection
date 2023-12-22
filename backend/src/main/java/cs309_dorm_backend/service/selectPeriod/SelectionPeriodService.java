package cs309_dorm_backend.service.selectPeriod;

import cs309_dorm_backend.domain.SelectionPeriod;

import java.util.List;

public interface SelectionPeriodService {

    SelectionPeriod update(SelectionPeriod selectionPeriod);

    SelectionPeriod findById(Integer periodId);

    List<SelectionPeriod> findAll();
}
