package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Teacher;

import java.util.List;

public interface TeacherService {
    public List<Teacher> findAll();

    public void deleteById(long id);

    public Teacher save(Teacher teacher);


}
