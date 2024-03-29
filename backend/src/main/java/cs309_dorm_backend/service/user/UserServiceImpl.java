package cs309_dorm_backend.service.user;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.UserRepo;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cs309_dorm_backend.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByCampusId(String campusId) {
        return userRepo.findById(campusId).orElse(null);
    }

    @Override
//    public Boolean checkLogin(UserDto userInfo) {
    public Boolean checkLogin(UserDto userInfo, HttpSession session, Map<String, String> userSessions) {
        if (userInfo.getCampusId() == null || userInfo.getCampusId().isEmpty()) {
            throw new MyException(1, "Campus ID shouldn't be null");
        }
        if (userInfo.getPassword().isEmpty()) {
            throw new MyException(2, "Password shouldn't be null");
        }
        User user = userRepo.findUserByCampusId(userInfo.getCampusId());
        if (user == null) {
            throw new MyException(3, "user Not found");
        }
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                return BCryptPasswordEncoder.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                return BCryptPasswordEncoder.matches(rawPassword.toString(), encodedPassword);
            }
        };
        boolean passwordMatch = passwordEncoder.matches(userInfo.getPassword(), user.getPassword());
        String userId = user.getCampusId();
        String sessionId = session.getId();
//        String uniqueId = (String) session.getAttribute("uniqueId");
//        if (uniqueId == null || uniqueId.equals(userSessions.get(userId))) {
//             唯一标识符匹配，继续访问
//            return false;
//        }
        if (!passwordMatch) {
            log.info("User {} login fail", userInfo.getCampusId());
            throw new MyException(4, "Wrong password");
        }
        log.info("User {} login success", userInfo.getCampusId());
        return true;
    }

    public void encodePassword() {
        List<User> users = userRepo.findAll();
        for (User user : users) {
            String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepo.save(user);
        }
    }

    private boolean checkPasswordEquals(String givenPassword, String encodedPassword) {
        BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
        String givenEncodePassword = BCryptPasswordEncoder.encode(givenPassword);
        return BCryptPasswordEncoder.matches(givenEncodePassword, encodedPassword);
    }


    @Override
    public UserDto updatePassword(UserUpdateDto userUpdateDto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new MyException(4, errors.get(0).getDefaultMessage());
        }
        String campusId = userUpdateDto.getCampusId();
        User user = userRepo.findUserByCampusId(campusId);
        if (user == null) {
            throw new MyException(404, "user " + campusId + " not found");
        }
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                return BCryptPasswordEncoder.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                return BCryptPasswordEncoder.matches(rawPassword.toString(), encodedPassword);
            }
        };

        boolean passwordMatch = passwordEncoder.matches(userUpdateDto.getOldPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new MyException(5, "Old password is wrong");
        }
//        if (!user.getPassword().equals(userUpdateDto.getOldPassword())) {
//            throw new MyException(5, "Old password is wrong");
//        }
//        user.setPassword(userUpdateDto.getNewPassword());
        return saveEncodePassword(user,userUpdateDto.getNewPassword());
//        return save(user);
    }

    @Override
    public UserDto resetPassword(UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new MyException(4, errors.get(0).getDefaultMessage());
        }
        String campusId = userDto.getCampusId();
        User user = userRepo.findUserByCampusId(campusId);
        if (user == null) {
            throw new MyException(404, "user " + campusId + " not found");
        }
        return saveEncodePassword(user,userDto.getPassword());
    }

    @Override // create or update
    public UserDto save(User userInfo) {
        User user = userRepo.save(userInfo);
        return convertUser(user);
    }

    @Override
    public UserDto register(@Valid UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new MyException(4, errors.get(0).getDefaultMessage());
        } else if (!userForm.checkPasswordEquals()) {
            throw new MyException(5, "Password and confirm password are not equal");
        } else if (userRepo.findUserByCampusId(userForm.getCampusId()) != null) {
            throw new MyException(6, "Campus ID already exists");
        } else {
//            return save(userForm.convertToUser());
            return saveEncodePassword(userForm.convertToUser(), userForm.getPassword());
        }
    }

    private UserDto saveEncodePassword(User user,String newPassword) {
        String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encodedPassword);
        return save(user);
    }

    @Override
    public boolean deleteByCampusId(String campusId) {
        Optional<User> userOptional = userRepo.findById(campusId);
        if (userOptional.isPresent()) {
            userRepo.deleteById(campusId);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }

    @Override
    public void updateName(String campusId, String name) {
        User user = userRepo.findUserByCampusId(campusId);
        if (user == null) {
            throw new MyException(404, "user " + campusId + " not found");
        }
        user.setName(name);
        save(user);
    }

    private UserDto convertUser(User user) {
        return UserDto.builder()
                .campusId(user.getCampusId())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}

