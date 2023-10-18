package cs309_dorm_backend.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.TeamMember;
import java.util.List;
public interface TeamMemberRepo extends JpaRepository<TeamMember,Long>{
    public List<TeamMember> findAll();
}
