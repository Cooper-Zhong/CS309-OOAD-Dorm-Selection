package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.SelectionPeriod;
import java.util.List;

public interface SelectionPeriodRepos extends JpaRepository<SelectionPeriod,Long> {
    public List<SelectionPeriod> findAll();
}
