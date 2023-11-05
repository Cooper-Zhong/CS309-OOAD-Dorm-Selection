package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByCampusId(int campusId);

    User findUserByCampusIdAndPassword(int campusId, String password);

}
