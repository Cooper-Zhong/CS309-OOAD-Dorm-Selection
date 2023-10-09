package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Selection;
import java.util.List;
public interface SelectionRepos extends JpaRepository<Selection,Long> {
    public List<Selection> findAll();
}
