package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.User;

import java.util.List;

/**
 * 用户业务逻辑接口
 *
 * @author pan_junbiao
 **/
public interface UserService {
    User findById(int userId);

    List<User> findAll();

    User save(User userInfo);

    boolean deleteById(int userId);


}