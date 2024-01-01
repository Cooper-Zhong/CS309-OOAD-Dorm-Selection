package cs309_dorm_backend.service.user;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.UserRepo;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
//import jodd.crypt.BCrypt;
//import jdk.internal.access.JavaIOFileDescriptorAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cs309_dorm_backend.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
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
                String encodedPassword = BCryptPasswordEncoder.encode(rawPassword.toString());
//                String encodedPassword = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
                return encodedPassword;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
                boolean passwordMatches = BCryptPasswordEncoder.matches(rawPassword.toString(), encodedPassword);
//                boolean passwordMatches = BCrypt.checkpw(rawPassword.toString(), encodedPassword);
                return passwordMatches;
            }
        };
        boolean passwordMatch = passwordEncoder.matches(userInfo.getPassword(), user.getPassword());
//        if (!user.getPassword().equals(userInfo.getPassword())) {
        String userId = user.getCampusId();
        String sessionId = session.getId();
        String uniqueId = (String) session.getAttribute("uniqueId");
        if (uniqueId == null || uniqueId.equals(userSessions.get(userId))) {
            // 唯一标识符匹配，继续访问
            return false;
        }
        if (!passwordMatch) {
            throw new MyException(4, "Wrong password");
        }

        return true;
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
        if (!user.getPassword().equals(userUpdateDto.getOldPassword())) {
            throw new MyException(5, "Old password is wrong");
        }
        user.setPassword(userUpdateDto.getNewPassword());
        return save(user);

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
            return save(registerEncode(userForm.convertToUser()));
        }
    }

    private User registerEncode(User user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        return new User(user.getCampusId(), encodedPassword,user.getRole());
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

