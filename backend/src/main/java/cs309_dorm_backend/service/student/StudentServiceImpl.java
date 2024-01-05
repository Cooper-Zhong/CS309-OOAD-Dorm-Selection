package cs309_dorm_backend.service.student;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.StudentRepo;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserService userService;

    @Override
//    @Cacheable(value = "students")
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
//    @Cacheable(value = "students", key = "#id")
    public Student findById(String id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public String findNameById(String id) {
        return studentRepo.findNameById(id);
    }

    @Override
//    @CachePut(value = "students", key = "#student.studentId")
    public Student save(Student student) {
        String campusId = student.getStudentId();
        Student student1 = studentRepo.findById(campusId).orElse(null);
        if (student1 != null) { // student already exists
            throw new MyException(4, "student " + campusId + " already exists");
        } else {
            throw new MyException(5, "student " + campusId + "not exists" + "please register first");
        }
    }

    @Override
    public Student update(Student student) {
        String campusId = student.getStudentId();
        Student student1 = studentRepo.findById(campusId).orElse(null);
        if (student1 == null) { // student not found
            throw new MyException(404, "student " + campusId + " not found");
        } else {
            student.setUser(student1.getUser());
            userService.updateName(student1.getStudentId(), student1.getName());
            student.setTeam(student1.getTeam());
            return studentRepo.save(student);
        }
    }


    @Override
//    @CacheEvict(value = "students", key = "#studentId")
    public boolean deleteById(String studentId) {
        // set the corresponding team to null
        studentRepo.removeTeam(studentId);
        Optional<Student> studentOptional = studentRepo.findById(studentId);
        if (studentOptional.isPresent()) {
//            studentRepo.deleteById(studentId);
            studentRepo.deleteStudent(studentId);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }

    @Override
    public Team isInTeam(String studentId) {
        Student student = findById(studentId);
        if (student == null) {
            throw new MyException(4, "student " + studentId + " not found");
        } else {
            return student.getTeam();
        }
    }
}
