package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.TeacherRepo;
import cs309_dorm_backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    @Override
    public List<Teacher> findAll() {
        return teacherRepo.findAll();
    }

    @Override
    public void deleteById(long id) {
        teacherRepo.deleteById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepo.save(teacher);
    }
}
