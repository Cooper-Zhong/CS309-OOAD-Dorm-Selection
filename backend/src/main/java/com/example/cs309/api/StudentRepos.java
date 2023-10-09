package backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.Student;
import java.util.List;
public interface StudentRepos extends JpaRepository<Student,Long> {
    public List<Student> findAll();
}
