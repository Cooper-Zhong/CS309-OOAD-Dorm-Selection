package cs309_dorm_backend.service.student;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.StudentRepo;
import cs309_dorm_backend.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public Student save(Student student) {
        int campusId = student.getStudentId();
        Student student1 = studentRepo.findById(campusId).orElse(null);
        if (student1 != null) { // student already exists
            throw new MyException(4, "student " + campusId + " already exists");
        } else {
            throw new MyException(5, "student " + campusId + "not exists" + "please register first");
        }
    }

    @Override
    public Student update(Student student) {
        int campusId = student.getStudentId();
        Student student1 = studentRepo.findById(campusId).orElse(null);
        if (student1 == null) { // student not found
            throw new MyException(404, "student " + campusId + " not found");
        } else {
            student.setUser(student1.getUser());
            return studentRepo.save(student);
        }
    }


    @Override
    public boolean deleteById(int id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        if (studentOptional.isPresent()) {
            studentRepo.deleteById(id);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }
}
