package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public List<User> findAll();

    public User findByCampusId(String campusId);
}
