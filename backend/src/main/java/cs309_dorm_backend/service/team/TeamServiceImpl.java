package cs309_dorm_backend.service.team;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.TeamRepo;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.AlterLeaderDto;
import cs309_dorm_backend.dto.FavoriteDto;
import cs309_dorm_backend.dto.TeamMemberDto;
import cs309_dorm_backend.service.room.RoomService;
import cs309_dorm_backend.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Service
public class TeamServiceImpl implements TeamService {


    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RoomService roomService;

    @Override
    public Team findById(int id) {
        return teamRepo.findById(id).orElse(null);
    }

    @Override
    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    @Override
    public Team findByCreator(String creatorId) {
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
    public boolean deleteTeamByCreator(String creatorId) {
        Team team = findByCreator(creatorId);
        if (team == null) { // if the team does not exist
            throw new MyException(404, "team created by " + creatorId + " does not exist");
        }
        // set team_id to null for all members, remove foreign key constraint!
        Set<Student> members = team.getTeamMembers();
        for (Student member : members) {
            teamRepo.removeStudentTeam(member.getStudentId());
        }
        //remove favorite rooms ( on delete cascade )
        teamRepo.deleteByCreator(creatorId); // delete the team
        return true;
    }

    @Override
    public boolean deleteMember(String studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) { // if the student does not exist
            throw new MyException(4, "student " + studentId + " does not exist");
        }
        Team team = student.getTeam();
        if (team == null) { // if the student is not in a team
            throw new MyException(5, "student " + studentId + " is not in a team");
        }
        if (team.getCreatorId().equals(studentId)) { // if the student is the creator of the team
            throw new MyException(6, "student " + studentId + " is the creator of the team");
        }
        teamRepo.removeStudentTeam(studentId);
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
        String creatorId = team.getCreatorId();
        Student creator = studentService.findById(creatorId);
        if (creator == null) { // if the creator does not exist
            throw new MyException(4, "student " + creatorId + " does not exist");
        }
        if (creator.getTeam() != null) { // if the creator is already in a team
            throw new MyException(5, "student " + creatorId + " is already in a team");
        }
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
        String creatorId = teamMemberDto.getCreatorId();
        String memberId = teamMemberDto.getStudentId();
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
        Set<Student> members = team.getTeamMembers();
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
    public Team updateTeam(Team team) {
        String creatorId = team.getCreatorId();
        Team oldTeam = findByCreator(creatorId);
        if (oldTeam == null) { // if the team does not exist
            throw new MyException(4, "team created by " + creatorId + " does not exist");
        } else {
            team.setTeamId(oldTeam.getTeamId());
//            String creatorId1 = team.getCreatorId();
//            oldTeam.setCreatorId(creatorId1);
//            oldTeam.setTeamName(team.getTeamName());
//            oldTeam.setTeamInfo(team.getTeamInfo());
//            save(oldTeam);
            return save(team);
        }
    }

    @Override
    public Student alterLeader(AlterLeaderDto alterLeaderDto) {
        String oldId = alterLeaderDto.getOldId();
        String leaderId = alterLeaderDto.getLeaderId();

        Student creator = studentService.findById(oldId);
        Student newleader = studentService.findById(leaderId);
        if (creator == null) { // if the creator does not exist
            throw new MyException(4, "student " + oldId + " does not exist");
        }
        if (newleader == null) { // if the student does not exist
            throw new MyException(4, "student " + leaderId + " does not exist");
        }
        Team team = findByCreator(oldId);
        if (team == null) { // if the team does not exist
            throw new MyException(5, "team created by " + oldId + " does not exist");
        }
        Team memberTeam = newleader.getTeam();
        if (memberTeam == null || memberTeam.getTeamId() != team.getTeamId()) { // if the student is not in this team
            throw new MyException(6, "student " + leaderId + " is not in this team");
        }
        teamRepo.updateTeamCreator(oldId, leaderId);
        return newleader;
    }

    @Override
    public boolean favoriteRoom(FavoriteDto favoriteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyException(400, bindingResult.getFieldError().getDefaultMessage());
        }
        String studentId = favoriteDto.getStudentId();
        int buildingId = favoriteDto.getBuildingId();
        int roomNumber = favoriteDto.getRoomNumber();
        Student student = studentService.findById(studentId);
        if (student == null) { // if the student does not exist
            throw new MyException(4, "student " + studentId + " does not exist");
        }
        Team team = student.getTeam();
        if (team == null) { // if the student is not in a team
            throw new MyException(5, "student " + studentId + " is not in a team");
        }
        Room room = roomService.findOne(buildingId, roomNumber);
        if (room == null) { // if the room does not exist
            throw new MyException(6, "room " + buildingId + "-" + roomNumber + " does not exist");
        }
        try {
            teamRepo.addFavoriteRoom(team.getTeamId(), room.getRoomId());
        } catch (Exception e) {
            // if the room is already in the favorite list, do nothing
        }
        return true;
    }

    @Override
    public boolean unfavoriteRoom(FavoriteDto favoriteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyException(400, bindingResult.getFieldError().getDefaultMessage());
        }
        String studentId = favoriteDto.getStudentId();
        int buildingId = favoriteDto.getBuildingId();
        int roomNumber = favoriteDto.getRoomNumber();
        Student student = studentService.findById(studentId);
        if (student == null) { // if the student does not exist
            throw new MyException(4, "student " + studentId + " does not exist");
        }
        Team team = student.getTeam();
        if (team == null) { // if the student is not in a team
            throw new MyException(5, "student " + studentId + " is not in a team");
        }
        Room room = roomService.findOne(buildingId, roomNumber);
        if (room == null) { // if the room does not exist
            throw new MyException(6, "room " + buildingId + "-" + roomNumber + " does not exist");
        }
        try {
            teamRepo.removeFavoriteRoom(team.getTeamId(), room.getRoomId());
        } catch (Exception e) {
            // if the room is not in the favorite list, do nothing
        }
        return true;

    }
}
