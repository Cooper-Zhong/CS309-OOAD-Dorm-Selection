package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Teacher;
import java.util.List;
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    public List<Teacher> findAll();
}
