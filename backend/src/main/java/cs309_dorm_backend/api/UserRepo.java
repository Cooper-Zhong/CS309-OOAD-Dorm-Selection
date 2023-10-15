package cs309_dorm_backend.api;
import cs309_dorm_backend.domain.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface UserRepo extends CrudRepository<User,Long>{
    public List<User> findAll();
}
