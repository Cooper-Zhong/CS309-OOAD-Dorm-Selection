package backend.api;
import backend.domain.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface UserRepos extends CrudRepository<User,Long>{
    public List<User> findAll();
}
