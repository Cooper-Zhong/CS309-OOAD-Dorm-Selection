package backend.api;

import backend.domain.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PersonalRepos extends JpaRepository<Personal,Long> {
    public List<Personal> findAll();
}
