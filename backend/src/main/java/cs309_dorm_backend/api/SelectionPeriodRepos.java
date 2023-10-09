package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.SelectionPeriod;

public interface SelectionPeriodRepos extends JpaRepository<SelectionPeriod,Long> {
    public List<SelectionPeriod> findAll();
}
