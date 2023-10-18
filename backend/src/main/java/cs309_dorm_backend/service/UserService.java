package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.User;

import java.util.List;

public interface UserService {

    public User getUserByCampusId(String campusId);

    public User save(User user);

    public void deleteById(long id);

    public List<User> findAll();
}
