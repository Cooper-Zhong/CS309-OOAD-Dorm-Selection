package cs309_dorm_backend.service.team;
import cs309_dorm_backend.domain.Team;
import java.util.List;

public interface TeamService {
    List<Team> findAll();
    Team findOne(int id);
    boolean deleteById(int id);
    Team addOne(Team team);
    Team save(Team team);
    Team update(Team team);
}
