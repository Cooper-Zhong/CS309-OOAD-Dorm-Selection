package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Faculty;
import java.util.List;
public interface FacultyRepos extends JpaRepository<FacultyRepos,Long> {
    public List<FacultyRepos> findAll();
}
