package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface StudentRepo extends CrudRepository<Student, Integer> {

    List<Student> findAll();

    Student save(Student studentInfo);

    boolean deleteById(int campusId);

    Student findById(int campusId);
}
