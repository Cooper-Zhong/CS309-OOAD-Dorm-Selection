package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Teacher;
import cs309_dorm_backend.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/teacher")
@Api(tags = "Teacher Controller")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/findAll")
    public List<Teacher> findAll() {
        return teacherService.findAll();
    }

    @GetMapping("/findById/{teacherId}")
    public Teacher findById(@PathVariable int teacherId) {
        return teacherService.findById(teacherId);
    }

    @DeleteMapping("/deleteById/{teacherId}")
    public boolean deleteById(@PathVariable int teacherId) {
        return teacherService.deleteById(teacherId);
    }


    /**
     * update teacher info
     *
     * @return 200: success; 404: teacher not found; 400: invalid key
     */
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Teacher teacher) {
        int teacherId = teacher.getTeacherId();
        Teacher teacher1 = teacherService.findById(teacherId);
        if (teacher1 == null) {
            return ResponseEntity.status(404).body("teacher " + teacherId + " not found");
        } else {
            teacher.setUser(teacher1.getUser()); // user cannot be changed, assign primary key
            teacherService.save(teacher);
            return ResponseEntity.ok("teacher " + teacherId + " updated");
        }
    }


}
