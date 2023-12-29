package cs309_dorm_backend.service.teacher;

import cs309_dorm_backend.domain.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(String teacherId);

    Teacher save(Teacher teacherInfo);

    Teacher update(Teacher teacherInfo);

    boolean deleteById(String teacherId);
}
