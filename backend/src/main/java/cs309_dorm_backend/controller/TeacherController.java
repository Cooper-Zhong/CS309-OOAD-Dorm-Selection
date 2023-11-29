package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Teacher;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Api(tags = "Teacher Controller")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

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
    public List<Teacher> findAll() {
        return teacherService.findAll();
    }

    @GetMapping("/findById/{teacherId}")
    public GlobalResponse findById(@PathVariable String teacherId) {
        Teacher teacher = teacherService.findById(teacherId);
        if (teacher == null) {
            return new GlobalResponse<>(1, "teacher not found", null);
        } else {
            return new GlobalResponse<>(0, "success", teacher);
        }
    }

    @DeleteMapping("/deleteById/{teacherId}")
    public GlobalResponse deleteById(@PathVariable String teacherId) {
        boolean result = teacherService.deleteById(teacherId);
        if (result) {
            return new GlobalResponse<>(0, "success", null);
        } else {
            return new GlobalResponse<>(1, "teacher not found", null);
        }
    }

    @PutMapping("/update")
    public GlobalResponse update(@RequestBody Teacher teacher) {
        Teacher teacher1 = teacherService.update(teacher);
        if (teacher1 == null) {
            return new GlobalResponse<>(1, "teacher not found", null);
        } else {
            return new GlobalResponse<>(0, "success", teacher1);
        }
    }


}
