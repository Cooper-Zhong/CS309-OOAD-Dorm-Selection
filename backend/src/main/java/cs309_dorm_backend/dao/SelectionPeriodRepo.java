package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.SelectionPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectionPeriodRepo extends JpaRepository<SelectionPeriod, Integer> {
}

