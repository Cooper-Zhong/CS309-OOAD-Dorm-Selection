package cs309_dorm_backend.service.user;

import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    UserDto updatePassword(UserUpdateDto userUpdateDto, BindingResult result);
    User findByCampusId(String campusId);

    Boolean checkLogin(UserDto userInfo);

    UserDto register(@Valid UserForm userForm, BindingResult result);

    List<User> findAll();

    UserDto save(User userInfo);

    boolean deleteByCampusId(String campusId);


}