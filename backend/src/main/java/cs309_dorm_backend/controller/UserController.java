package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    @ApiOperation(value = "Find all users")
    @ApiResponse(code = 200, message = "OK", response = User.class, responseContainer = "List")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * find a user by id
     *
     * @return success: 200 user info; fail: 404.
     */
    @GetMapping("/findById/{campusId}")
    @ApiOperation(value = "Find a user by id", notes = "Get user information by their campus ID.")
    public User findById(
            @ApiParam(value = "campus ID", required = true)
            @PathVariable int campusId) {
        return userService.findById(campusId);
    }

    /**
     * add a new user，simultaneously add a new student/student.
     *
     * @return success: 200 user info; fail: 400.
     */
    @PostMapping("/save")
    @ApiOperation(value = "Add a new user", notes = "Add a new user to the database.")
    // if the id is not auto-increment, must be manually assigned before calling save():
    public ResponseEntity<String> addOne(@RequestBody User user) {
        User user1 = userService.findById(user.getCampusId());
        int campusId = user.getCampusId();
        if (user1 == null) { // if the user does not exist
            userService.save(user);
            String role = user.getRole();
            return ResponseEntity.ok("user " + campusId + " added" +
                    " as " + role);
        } else {
            return ResponseEntity.status(400).body(campusId + " already exists");
        }
    }

    /**
     * update user password
     *
     * @return 200: password updated; 404: user not found; 400: wrong password
     */
    @PutMapping("/update")
    @ApiOperation(value = "update user password", notes = "update user password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "password updated", response = String.class),
            @ApiResponse(code = 400, message = "wrong password"),
            @ApiResponse(code = 404, message = "user not found")})
    public ResponseEntity<String> update(@RequestBody Map<String, String> request) {
        int campusId = Integer.parseInt(request.get("campusId"));
        String oldPassword = request.get("old");
        String newPassword = request.get("new");
        User user = userService.findById(campusId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else if (!user.getPassword().equals(oldPassword)) { // old password is wrong
            return ResponseEntity.status(400).body("wrong password");
        } else { // update password
            user.setPassword(newPassword);
            userService.save(user);
            return ResponseEntity.ok("password updated");
        }
    }

    /**
     * delete a user by id
     *
     * @return 200: user deleted; 404: user not found.
     */
    @DeleteMapping("/deleteById/{campusId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user deleted"),
            @ApiResponse(code = 404, message = "user not found")})
    @ApiOperation(value = "Delete a user by id", notes = "Delete a user by their campus ID.")
    public boolean deleteById(@PathVariable int campusId) {
        return userService.deleteById(campusId);
    }

}
