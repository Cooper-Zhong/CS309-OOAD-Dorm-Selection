package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
import cs309_dorm_backend.service.user.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(tags = "User Controller")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private Map<String, String> userSessions = new HashMap<>();

    @GetMapping("/encode")
    public GlobalResponse encodePassword() {
        userService.encodePassword();
        return GlobalResponse.builder()
                .code(0)
                .msg("Encode password successfully.")
                .build();
    }




    @PostMapping("/login")
//    public GlobalResponse checkLogin(@RequestBody UserDto userDto) {
    public GlobalResponse checkLogin(@RequestBody UserDto userDto, HttpSession session) {
        String userId = userDto.getCampusId();
        String sessionId = session.getId();
        String uniqueId = (String) session.getAttribute("uniqueId");
        if (userService.checkLogin(userDto, session, userSessions)) {
//        if (userService.checkLogin(userDto)) {
                userSessions.put(userId, sessionId);
//                session.setAttribute("uniqueId", uniqueId);
                return GlobalResponse.<User>builder()
                        .code(0)
                        .msg("Login successfully.")
                        .data(userService.findByCampusId(userDto.getCampusId()))
                        .build();
            } else {
                return GlobalResponse.<String>builder()
                        .code(1)
                        .msg("login fail")
                        .build();
            }
    }

    // Handling OPTIONS request explicitly

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @PostMapping("/register")
    public GlobalResponse registerUser(@RequestBody @Valid UserForm userForm, BindingResult result) {
        UserDto userDto = userService.register(userForm, result);
        log.info("User {} register success", userDto.getCampusId());
        return GlobalResponse.builder()
                .code(0)
                .msg("Register successfully.")
                .data(userDto)
                .build();
    }

    @PostMapping("/updatePassword")
    public GlobalResponse updatePassword(@RequestBody @Valid UserUpdateDto userUpdateDto, BindingResult result) {
        UserDto userDto = userService.updatePassword(userUpdateDto, result);
        log.info("User {} update password success", userDto.getCampusId());
        return GlobalResponse.builder()
                .code(0)
                .msg("Edit password successfully.")
                .data(userDto)
                .build();
    }

    @PostMapping("/resetPassword")
    public GlobalResponse resetPassword(@RequestBody @Valid UserDto userDto, BindingResult result) {
        log.info("User {} reset password success", userDto.getCampusId());
        userService.resetPassword(userDto, result);
        return GlobalResponse.builder()
                .code(0)
                .msg("Reset password successfully.")
                .data(null)
                .build();
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Find all users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findById/{campusId}")
    @ApiOperation(value = "Find a user by id", notes = "Get user information by their campus ID.")
    public User findById(@PathVariable String campusId) {
        return userService.findByCampusId(campusId);
    }

    /**
     * delete a user by id
     *
     * @return 200: user deleted; 404: user not found.
     */

    @DeleteMapping("/deleteById/{campusId}")
    @ApiOperation(value = "Delete a user by id", notes = "Delete a user by their campus ID.")
    public GlobalResponse deleteById(@PathVariable String campusId) {
        if (userService.deleteByCampusId(campusId)) {
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Delete user successfully.")
                    .build();
        } else {
            return GlobalResponse.builder()
                    .code(1)
                    .msg("Delete user fail.")
                    .build();
        }
    }

//
//    /**
//     * add a new user，simultaneously add a new student/student.
//     *
//     * @return success: 200 user info; fail: 400.
//     */
//    @PostMapping("/save")
//    @ApiOperation(value = "Add a new user", notes = "Add a new user to the database.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = String.class),
//            @ApiResponse(code = 400, message = "user already exists")})
//    // if the id is not auto-increment, must be manually assigned before calling save():
//    public ResponseEntity<String> addOne(@RequestBody User user) {
//        User user1 = userService.findById(user.getCampusId());
//        int campusId = user.getCampusId();
//        if (user1 == null) { // if the user does not exist
//            userService.save(user);
//            String role = user.getRole();
//            return ResponseEntity.ok("user " + campusId + " added" +
//                    " as " + role);
//        } else {
//            return ResponseEntity.status(400).body(campusId + " already exists");
//        }

//    }
////
//    /**
//     * update user password
//     *
//     * @return 200: password updated; 404: user not found; 400: wrong password
//     */
//    @PutMapping("/update")
//    @ApiOperation(value = "update user password", notes = "update user password")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "password updated", response = String.class),
//            @ApiResponse(code = 400, message = "wrong password"),
//            @ApiResponse(code = 404, message = "user not found")})
//    public ResponseEntity<String> update(@RequestBody Map<String, String> request) {
//        int campusId = Integer.parseInt(request.get("campusId"));
//        String oldPassword = request.get("old");
//        String newPassword = request.get("new");
//        User user = userService.findById(campusId);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        } else if (!user.getPassword().equals(oldPassword)) { // old password is wrong
//            return ResponseEntity.status(400).body("wrong password");
//        } else { // update password
//            user.setPassword(newPassword);
//            userService.save(user);
//            return ResponseEntity.ok("password updated");
//        }

//    }

}
