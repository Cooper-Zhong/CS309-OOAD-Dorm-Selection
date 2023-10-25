package cs309_dorm_backend.service;

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
        return studentRepo.save(student);
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
