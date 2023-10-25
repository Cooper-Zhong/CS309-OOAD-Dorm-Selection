package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.TeacherRepo;
import cs309_dorm_backend.domain.Teacher;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;


    @Override
    public List<Teacher> findAll() {
        return teacherRepo.findAll();
    }

    @Override
    public Teacher findById(int teacherId) {
        return teacherRepo.findById(teacherId).orElse(null);
    }

    @Override
    public Teacher save(Teacher teacherInfo) {
        return teacherRepo.save(teacherInfo);
    }

    @Override
    public boolean deleteById(int teacherId) {
        Optional<Teacher> teacherOptional = teacherRepo.findById(teacherId);
        if (teacherOptional.isPresent()) {
            teacherRepo.deleteById(teacherId);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }
}
