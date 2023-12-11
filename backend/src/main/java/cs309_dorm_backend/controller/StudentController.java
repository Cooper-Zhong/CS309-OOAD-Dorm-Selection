package cs309_dorm_backend.controller;

import cn.keking.anti_reptile.annotation.AntiReptile;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @AntiReptile
    @GetMapping("/findAll")
    public List<Student> findAll() {
        return studentService.findAll();
    }
    @AntiReptile
    @GetMapping("/findById/{studentId}")
    public GlobalResponse findById(@PathVariable int studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) {
            return new GlobalResponse<>(1, "student not found", null);
        } else {
            return new GlobalResponse<>(0, "success", student);
        }
    }


//    @PostMapping("/save")
//    // if the id is not auto-increment, must be manually assigned before calling save():
//    public Student addOne(@RequestBody Student student) {
//        return studentService.save(student);
//    }


    @PutMapping("/update")
    public GlobalResponse update(@RequestBody Student student) {
        Student student1 = studentService.update(student);
        if (student1 == null) {
            return new GlobalResponse<>(1, "student not found", null);
        } else {
            return new GlobalResponse<>(0, "success", student1);
        }
    }


    @DeleteMapping("/deleteById/{campusId}")
    public GlobalResponse deleteById(@PathVariable int campusId) {
        boolean result = studentService.deleteById(campusId);
        if (result) {
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "student not found", null);
        }
    }

}
