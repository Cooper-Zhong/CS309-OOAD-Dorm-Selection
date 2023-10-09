package backend.api;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Faculty;
import java.util.List;
public interface FacultyRepos extends JpaRepository<FacultyRepos,Long> {
    public List<FacultyRepos> findAll();
}
