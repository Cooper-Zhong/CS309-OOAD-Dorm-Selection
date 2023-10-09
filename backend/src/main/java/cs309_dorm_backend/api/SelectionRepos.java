package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.Selection;

public interface SelectionRepos extends JpaRepository<Selection,Long> {
    public List<Selection> findAll();
}
