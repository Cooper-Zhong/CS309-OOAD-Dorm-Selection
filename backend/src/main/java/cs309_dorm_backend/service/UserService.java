package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.User;

import java.util.List;

public interface UserService {
    User findById(int userId);

    List<User> findAll();

    User save(User userInfo);

    boolean deleteById(int userId);


}