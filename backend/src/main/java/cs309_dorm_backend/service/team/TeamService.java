package cs309_dorm_backend.service.team;

import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.AlterLeaderDto;
import cs309_dorm_backend.dto.FavoriteDto;
import cs309_dorm_backend.dto.SelectDto;
import cs309_dorm_backend.dto.TeamMemberDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TeamService {

    Team findById(int id);
    List<Team> findAll();

    Team findByCreator(String creatorId);

    Team isInTeam(String studentId);

    boolean deleteTeamByCreator(String creatorId);

    boolean deleteMember(String studentId);

    Team save(Team team);

    Team addTeam(Team team, BindingResult bindingResult);

    Student addMember(TeamMemberDto teamMemberDto, BindingResult bindingResult);

    Team updateTeam(Team team);

    Student alterLeader(AlterLeaderDto alterLeaderDto);

    Room selectRoom(SelectDto selectDto);

    Room findSelectedRoom(int teamId);

    boolean favoriteRoom(FavoriteDto favoriteDto, BindingResult bindingResult); // add a favorite room for a team that the student is in

    boolean unfavoriteRoom(FavoriteDto favoriteDto, BindingResult bindingResult); // remove a favorite room for a team that the student is in
}
