package cs309_dorm_backend.service.user;

import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.UserDto;
import cs309_dorm_backend.dto.UserForm;
import cs309_dorm_backend.dto.UserUpdateDto;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDto updatePassword(UserUpdateDto userUpdateDto, BindingResult result);

    UserDto resetPassword(UserDto userDto, BindingResult result);

    User findByCampusId(String campusId);

    Boolean checkLogin(UserDto userInfo, HttpSession session, Map<String, String> userSessions);
//    Boolean checkLogin(UserDto userInfo);

    void encodePassword();

    void decodePassword();


    UserDto register(@Valid UserForm userForm, BindingResult result);

    List<User> findAll();

    UserDto save(User userInfo);

    boolean deleteByCampusId(String campusId);

    void updateName(String campusId, String name);


}