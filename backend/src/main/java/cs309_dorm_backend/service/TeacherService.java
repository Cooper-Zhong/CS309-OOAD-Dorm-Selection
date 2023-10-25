package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(int teacherId);

    Teacher save(Teacher teacherInfo);

    boolean deleteById(int teacherId);
}
