package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Team;
import java.util.List;

public interface TeamRepos extends JpaRepository<Team,Long> {
    public List<Team> findAll();
}
