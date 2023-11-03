package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/findAll")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/findById/{studentId}")
    public Student findById(@PathVariable int studentId) {
        return studentService.findById(studentId);
    }


//    @PostMapping("/save")
//    // if the id is not auto-increment, must be manually assigned before calling save():
//    public Student addOne(@RequestBody Student student) {
//        return studentService.save(student);
//    }


    @PutMapping("/update")
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }


    @DeleteMapping("/deleteById/{campusId}")
    public boolean deleteById(@PathVariable int campusId) {
        return studentService.deleteById(campusId);
    }

}
