package cs309_dorm_backend.service.student;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(String id);

    Student save(Student student);

    Student update(Student student);

    boolean deleteById(String id);

    Team isInTeam(String studentId);


}
