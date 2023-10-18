package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import cs309_dorm_backend.domain.Student;
public interface StudentRepo extends JpaRepository<Student,Long> {
    public List<Student> findAll();
}
