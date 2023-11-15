package cs309_dorm_backend.service.team;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.TeamRepo;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.TeamMemberDto;
import cs309_dorm_backend.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private StudentService studentService;

    @Override
    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    @Override
    public Team findByCreator(int creatorId) {
        return teamRepo.findByCreator(creatorId);
    }


    /**
     * delete a team with creatorId = ?
     *
     * @param creatorId
     * @return
     */
    @Override
    @Transactional
    public boolean deleteByCreator(int creatorId) {
        Team team = findByCreator(creatorId);
        if (team == null) { // if the team does not exist
            throw new MyException(404, "team created by " + creatorId + " does not exist");
        }
        // set team_id to null for all members, remove foreign key constraint!
        List<Student> members = team.getTeamMembers();
        for (Student member : members) {
            teamRepo.removeStudentTeam(member.getStudentId());
        }
        teamRepo.deleteByCreator(creatorId); // delete the team
        return true;
    }

    @Override
    public Team save(Team team) {
        return teamRepo.save(team);
    }


    /**
     * add a team with creatorId = ?
     *
     * @param team
     * @param bindingResult
     * @return
     */
    @Override
    @Transactional
    public Team addTeam(@Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyException(400, bindingResult.getFieldError().getDefaultMessage());
        }
        int creatorId = team.getCreator().getStudentId();
        Student creator = studentService.findById(creatorId);
        if (creator == null) { // if the creator does not exist
            throw new MyException(404, "student " + creatorId + " does not exist");
        }
        Team team1 = findByCreator(creatorId);
        if (team1 != null) { // if the team already exists
            throw new MyException(400, "team created by " + creatorId + " already exists");
        }
        team.setCreator(creator);
        team = save(team);
        teamRepo.setTeam(creatorId, team.getTeamId()); // creator
        return team;
    }


    /**
     * add a member to a team with creatorId = ?
     *
     * @param teamMemberDto
     * @param bindingResult
     * @return
     */
    @Override
    @Transactional
    public Student addMember(TeamMemberDto teamMemberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyException(400, bindingResult.getFieldError().getDefaultMessage());
        }
        int creatorId = teamMemberDto.getCreatorId();
        int memberId = teamMemberDto.getStudentId();
        Student creator = studentService.findById(creatorId);
        Student member = studentService.findById(memberId);
        if (creator == null) { // if the creator does not exist
            throw new MyException(4, "student " + creatorId + " does not exist");
        }
        if (member == null) { // if the student does not exist
            throw new MyException(4, "student " + memberId + " does not exist");
        }
        Team team = findByCreator(creatorId);
        if (team == null) { // if the team does not exist
            throw new MyException(5, "team created by " + creatorId + " does not exist");
        }
        List<Student> members = team.getTeamMembers();
        Team memberTeam = member.getTeam();
        if (memberTeam != null) { // if the student is already in a team
            throw new MyException(6, "student " + memberId + " is already in a team");
        }
        if (members.size()>=4) { // if the team is full
            throw new MyException(7, "team is full");
        }
        teamRepo.setTeam(memberId, team.getTeamId());
        return member;
    }

    /**
     * update team name
     *
     * @param team
     * @return
     */
    @Override
    public Team updateTeamName(Team team) {
        int creatorId = team.getCreator().getStudentId();
        Team oldTeam = findByCreator(creatorId);
        if (oldTeam == null) { // if the team does not exist
            throw new MyException(404, "team created by " + creatorId + " does not exist");
        } else {
            Student creator = team.getCreator();
            oldTeam.setCreator(creator);
            oldTeam.setTeamName(team.getTeamName());
            save(oldTeam);
            return oldTeam;
        }
    }

    @Override
    public Team updateTeamCreator(TeamMemberDto teamMemberDto) {
        int creatorId = teamMemberDto.getCreatorId();
        int memberId = teamMemberDto.getStudentId();
        Student creator = studentService.findById(creatorId);
        Student member = studentService.findById(memberId);
        if (creator == null) { // if the creator does not exist
            throw new MyException(404, "student " + creatorId + " does not exist");
        }
        if (member == null) { // if the student does not exist
            throw new MyException(404, "student " + memberId + " does not exist");
        }
        Team team = findByCreator(creatorId);
        if (team == null) { // if the team does not exist
            throw new MyException(404, "team created by " + creatorId + " does not exist");
        }
        List<Student> members = team.getTeamMembers();
        Team memberTeam = member.getTeam();
        if (memberTeam != null) { // if the student is already in a team
            throw new MyException(404, "student " + memberId + " is already in a team");
        }
        if (members.size()>=4) { // if the team is full
            throw new MyException(404, "team is full");
        }
        teamRepo.setTeam(memberId, team.getTeamId());
        return team;
    }
}
