package backend.api;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.domain.TeamMember;
import java.util.List;
public interface TeamMemberRepos extends JpaRepository<TeamMember,Long>{
    public List<TeamMember> findAll();
}
