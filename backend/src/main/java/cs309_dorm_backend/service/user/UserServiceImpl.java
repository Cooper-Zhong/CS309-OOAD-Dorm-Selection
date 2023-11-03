package cs309_dorm_backend.service.user;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.UserRepo;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs309_dorm_backend.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;
import java.util.List;
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
    public User findByCampusId(int campusId) {
        return userRepo.findUserByCampusId(campusId);
    }

    @Override
    public Boolean checkLogin(UserDto userInfo) {
        if (userInfo.getCampusId() == 0) {
            throw new MyException(1, "Campus ID shouldn't be null");
        }
        if (userInfo.getPassword().isEmpty()) {
            throw new MyException(2, "Password shouldn't be null");
        }
        User user = userRepo.findUserByCampusIdAndPassword(userInfo.getCampusId(), userInfo.getPassword());
        if (user == null) {
            throw new MyException(3, "Wrong campus ID or password");
        }
        log.info("User {} login success", userInfo.getCampusId());
        return true;
    }

    @Override
    public UserDto updatePassword(UserUpdateDto userUpdateDto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new MyException(4, errors.get(0).getDefaultMessage());
        }
        int campusId = userUpdateDto.getCampusId();
        User user = userRepo.findUserByCampusId(campusId);
        if (user == null) {
            throw new MyException(404, "user " + campusId + " not found");
        }
        if (!user.getPassword().equals(userUpdateDto.getOldPassword())) {
            throw new MyException(5, "Old password is wrong");
        } else {
            user.setPassword(userUpdateDto.getNewPassword());
            return save(user);
        }
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
            return save(userForm.convertToUser());
        }
    }

    @Override
    public boolean deleteByCampusId(int campusId) {
        Optional<User> userOptional = userRepo.findById(campusId);
        if (userOptional.isPresent()) {
            userRepo.deleteById(campusId);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }

    private UserDto convertUser(User user) {
        return UserDto.builder()
                .campusId(user.getCampusId())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}
