package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Teacher;
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

    @PutMapping("/update")
    public Teacher update(@RequestBody Teacher teacher) {
        return teacherService.update(teacher);
    }


}
