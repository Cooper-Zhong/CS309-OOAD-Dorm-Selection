package cs309_dorm_backend.api;
import cs309_dorm_backend.domain.Users;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface UserRepos extends CrudRepository<Users,Long>{
    public List<Users> findAll();
}
