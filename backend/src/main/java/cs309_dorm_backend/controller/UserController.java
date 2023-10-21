package cs309_dorm_backend.controller;

import cs309_dorm_backend.dao.UserRepo;
import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<User> findAll() {
        List<User> users = userService.findAll();
        return users;
    }

    /**
     * find a user by id
     *
     * @return success: 200 user info; fail: 404.
     */
    @GetMapping("/findById/{campusId}")
    public ResponseEntity<User> findById(@PathVariable int campusId) {
        User user = userService.findById(campusId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    /**
     * add a new user
     *
     * @return success: 200 user info; fail: 400.
     */
    @PostMapping("/save")
    // if the id is not auto-increment, must be manually assigned before calling save():
    public ResponseEntity<String> addOne(@RequestBody User user) {
        User user1 = userService.findById(user.getCampusId());
        int campusId = user.getCampusId();
        if (user1 == null) { // if the user does not exist
            userService.save(user);
            return ResponseEntity.ok("user " + campusId + " added");
        } else {
            return ResponseEntity.status(400).body(campusId + " already exists");
        }
    }

    /**
     * update user password
     *
     * @return 200: password updated; 404: user not found; 400: same password; 401: old password is wrong.
     */
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Map<String, String> request) {
        int campusId = Integer.parseInt(request.get("campusId"));
        String oldPassword = request.get("old");
        String newPassword = request.get("new");
        User user = userService.findById(campusId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else if (user.getPassword().equals(oldPassword)) {
            if (user.getPassword().equals(newPassword)) { // same password
                return ResponseEntity.status(401).body("same password, try new one");
            } else {
                user.setPassword(newPassword);
                userService.save(user);
                return ResponseEntity.ok("password updated");
            }
        } else { // old password is wrong
            return ResponseEntity.status(400).body("wrong password");
        }
    }

    /**
     * delete a user by id
     *
     * @return 200: user deleted; 404: user not found.
     */
    @DeleteMapping("/deleteById/{campusId}")
    public ResponseEntity<String> deleteById(@PathVariable int campusId) {
        boolean result = userService.deleteById(campusId);
        if (result) {
            return ResponseEntity.ok("user deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
