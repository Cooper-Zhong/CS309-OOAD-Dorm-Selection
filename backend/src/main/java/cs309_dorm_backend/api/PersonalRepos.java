package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.Personal;
public interface PersonalRepos extends JpaRepository<Personal,Long> {
    public List<Personal> findAll();
}
