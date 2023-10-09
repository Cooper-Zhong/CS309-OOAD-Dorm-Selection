package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Team;

import java.util.List;

public interface TeamRepos extends JpaRepository<Team,Long> {
    public List<Team> findAll();
}
