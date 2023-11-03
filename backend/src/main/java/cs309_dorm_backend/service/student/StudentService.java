package cs309_dorm_backend.service.student;

import cs309_dorm_backend.domain.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(int id);

    Student save(Student student);

    Student update(Student student);

    boolean deleteById(int id);


}
