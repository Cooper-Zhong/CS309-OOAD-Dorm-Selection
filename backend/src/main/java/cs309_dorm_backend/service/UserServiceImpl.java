package cs309_dorm_backend.service;

import cs309_dorm_backend.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs309_dorm_backend.domain.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public User findById(int campusId) {
        return userRepo.findById(campusId).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override // create or update
    public User save(User userInfo) {
        return userRepo.save(userInfo);
    }

    @Override
    public boolean deleteById(int campusId) {
        Optional<User> userOptional = userRepo.findById(campusId);
        if (userOptional.isPresent()) {
            userRepo.deleteById(campusId);
            return true; // 删除成功
        } else {
            return false; // 实体不存在，无法删除
        }
    }


}

