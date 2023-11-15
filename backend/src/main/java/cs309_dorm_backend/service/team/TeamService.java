package cs309_dorm_backend.service.team;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.TeamMemberDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TeamService {
    List<Team> findAll();

    Team findByCreator(int creatorId);

    boolean deleteByCreator(int creatorId);

    Team save(Team team);

    Team addTeam(Team team, BindingResult bindingResult);

    Student addMember(TeamMemberDto teamMemberDto, BindingResult bindingResult);

    Team updateTeamName(Team team);

    Team updateTeamCreator(TeamMemberDto teamMemberDto); // admin for this team
}
