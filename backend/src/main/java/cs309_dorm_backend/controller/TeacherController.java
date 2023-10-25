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
     * @param map
     * @return 200: success; 404: teacher not found; 400: invalid key
     */
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Map<String, String> map) {
        int teacherId = Integer.parseInt(map.get("teacherId"));
        Teacher teacher = teacherService.findById(teacherId);
        if (teacher == null) {
            return ResponseEntity.status(404).body("teacher " + teacherId + " not found");
        }
        map.remove("teacherId");
        AtomicBoolean hasInvalidKey = new AtomicBoolean(false);
        map.forEach((k, v) -> {
            switch (k) {
                case "name":
                    teacher.setName(v);
                    break;
                default:
                    hasInvalidKey.set(true);
            }
        });
        if (hasInvalidKey.get()) {
            return ResponseEntity.status(400).body("invalid key");
        }

        teacherService.save(teacher);
        return ResponseEntity.ok("teacher " + teacherId + " updated"); // 200
    }


}
