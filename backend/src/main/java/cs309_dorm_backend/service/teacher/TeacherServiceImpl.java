package cs309_dorm_backend.service.teacher;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.TeacherRepo;
import cs309_dorm_backend.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Override
    public Teacher update(Teacher teacher) {
        int teacherId = teacher.getTeacherId();
        Teacher teacher1 = findById(teacherId);
        if (teacher1 == null) {
            throw new MyException(404, "teacher " + teacherId + " not found");
        } else {
            teacher.setUser(teacher1.getUser()); // user cannot be changed, assign primary key
            return teacherRepo.save(teacher);
        }
    }


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
