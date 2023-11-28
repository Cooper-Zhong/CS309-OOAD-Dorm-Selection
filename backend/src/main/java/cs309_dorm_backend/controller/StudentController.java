package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.service.student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }


    @GetMapping("/findAll")
    public List<Student> findAll() {
        return studentService.findAll();
    }

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


    @PostMapping("/update")
    public GlobalResponse update(@RequestBody Student student) {
        Student student1 = studentService.update(student);
        if (student1 == null) {
            return new GlobalResponse<>(1, "student not found", null);
        } else {
            log.info("Student {} updated", student1.getStudentId());
            return new GlobalResponse<>(0, "success", student1);
        }
    }


    @DeleteMapping("/deleteById/{campusId}")
    @Transactional
    public GlobalResponse deleteById(@PathVariable int campusId) {
        boolean result = studentService.deleteById(campusId);
        if (result) {
            log.info("Student {} deleted", campusId);
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "student not found", null);
        }
    }

}
