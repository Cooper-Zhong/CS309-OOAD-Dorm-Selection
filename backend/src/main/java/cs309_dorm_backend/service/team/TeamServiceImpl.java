package cs309_dorm_backend.service.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs309_dorm_backend.dao.TeamRepo;
import cs309_dorm_backend.domain.Team;
import java.util.List;
import java.util.Optional;


@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepo teamRepo;

    @Override
    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    @Override
    public Team findOne(int id) {
        long id1 = id;
        return teamRepo.findById(id1).orElse(null);
    }

    @Override
    public boolean deleteById(int id) {
        long id1 = id;
        Optional<Team> team = teamRepo.findById(id1);
        if (team.isPresent()) {
            teamRepo.deleteById(id1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Team addOne(Team team) {
        return save(team);
    }

    @Override
    public Team save(Team team) {
        return teamRepo.save(team);
    }

    @Override
    public Team update(Team team) {
        return save(team);
    }
}
