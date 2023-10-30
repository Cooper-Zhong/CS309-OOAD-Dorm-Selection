package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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


    /**
     * add a new student
     *
     * @return success: new student info; fail: null
     */
    @PostMapping("/save")
    // if the id is not auto-increment, must be manually assigned before calling save():
    public ResponseEntity<String> addOne(@RequestBody Student student) {
        int campusId = student.getStudentId();
        Student student1 = studentService.findById(campusId);
        if (student1 == null) {
            String msg = "user/student" + campusId + " does not exist " + "please create user first!";
            return ResponseEntity.status(404).body(msg);
        } else {
            student.setUser(student1.getUser());
            studentService.save(student);
            return ResponseEntity.ok("student " + campusId + " updated");
        }
    }

    /**
     * update student info
     *
     * @return 200: success; 404: student not found; 400: invalid key
     */
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Student student) {
        int campusId = student.getStudentId();
        Student student1 = studentService.findById(campusId);
        if (student1 == null) { // student does not exist
            return ResponseEntity.status(404).body("student " + campusId + " does not exist");
        } else {
            student.setUser(student1.getUser()); // change
            studentService.save(student);
            return ResponseEntity.ok("student " + campusId + " updated");
        }
    }


    /**
     * delete a student by id
     */
    @DeleteMapping("/deleteById/{campusId}")
    public boolean deleteById(@PathVariable int campusId) {
        return studentService.deleteById(campusId);
    }

}
