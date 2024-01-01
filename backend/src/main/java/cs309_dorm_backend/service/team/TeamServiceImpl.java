package cs309_dorm_backend.service.team;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.TeamRepo;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.AlterLeaderDto;
import cs309_dorm_backend.dto.FavoriteDto;
import cs309_dorm_backend.dto.SelectDto;
import cs309_dorm_backend.dto.TeamMemberDto;
import cs309_dorm_backend.service.notification.NotificationService;
import cs309_dorm_backend.service.room.RoomService;
import cs309_dorm_backend.service.student.StudentService;
import cs309_dorm_backend.websocket.NotificationWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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

    @Autowired
    private NotificationService notificationService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Team findById(int id) {
        return teamRepo.findById(id).orElse(null);
    }

    @Override
    public List<Team> findAll() {
        return teamRepo.findAll();
    }

    public Team isInTeam(String studentId) {
        return studentService.isInTeam(studentId);
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
        // remove favorite rooms ( on delete cascade )
        // remove assigned room, if any
        Room room = roomService.findSelectedRoom(team.getTeamId());
        if (room != null) {
            room.setSelectedTeam(null);
            roomService.save(room);
        }
        // remove team
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
        // send notification to member that is removed
        Notification notification = notificationService.createAndSaveNotification("system", studentId, "You have been removed from the team " + team.getTeamName() + ".");
        notificationService.save(notification);
        try {
            NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), studentId);
        } catch (Exception e) {
            throw new MyException(3, "websocket notification failed");
        }
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
        // send notification to team members
        for (Student member1 : members) {
            Notification notification = notificationService.createAndSaveNotification("system", member1.getStudentId(), "A new member " + member.getName() + " has joined the team.");
            notificationService.save(notification);
            try {
                NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), member1.getStudentId());
            } catch (Exception e) {
                throw new MyException(3, "websocket notification failed");
            }
        }
        // send notification to the new member
        Notification notification = notificationService.createAndSaveNotification("system", memberId, "You joined the team " + team.getTeamName() + " successfully.");
        notificationService.save(notification);
        try {
            NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), memberId);
        } catch (Exception e) {
            throw new MyException(3, "websocket notification failed");
        }
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
        // send notification to the new leader
        Notification notification = notificationService.createAndSaveNotification("system", leaderId, "You are the new leader of the team " + team.getTeamName() + ".");
        notificationService.save(notification);
        try {
            NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), leaderId);
        } catch (Exception e) {
            throw new MyException(3, "websocket notification failed");
        }

        teamRepo.updateTeamCreator(oldId, leaderId);
        return newleader;
    }

    @Override
    public Room selectRoom(SelectDto selectDto) {
        return roomService.selectRoom(selectDto);
    }

    @Override
    public Room unselectRoom(SelectDto selectDto) {
        return roomService.unselectRoom(selectDto);
    }

    @Override
    public Room findSelectedRoom(int teamId) {
        return roomService.findSelectedRoom(teamId);
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

    @Override
    @Transactional
    public void swapRoom(int applyRoomId, int acceptRoomId) {
        Room room1 = roomService.findById(applyRoomId);
        Room room2 = roomService.findById(acceptRoomId);
        if (room1 == null) {
            throw new MyException(404, "room " + applyRoomId + " does not exist");
        }
        if (room2 == null) {
            throw new MyException(404, "room " + acceptRoomId + " does not exist");
        }
        Team team1 = room1.getSelectedTeam();
        Team team2 = room2.getSelectedTeam();
        if (team1 == null) {
            throw new MyException(404, "room " + applyRoomId + " is not assigned to any team");
        }
        if (team2 == null) {
            throw new MyException(404, "room " + acceptRoomId + " is not assigned to any team");
        }
        room1.setSelectedTeam(team2);
        room2.setSelectedTeam(team1);
        roomService.save(room1);
        roomService.save(room2);
        Notification toApplicant = notificationService.createAndSaveNotification("system", team1.getCreatorId(), "Your swap room application has been accepted.");
        try {
            NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(toApplicant)), team1.getCreatorId());
        } catch (Exception e) {
            throw new MyException(3, "websocket notification failed");
        }
    }

    @Override
    public void applySwap(String applyCreatorId, String applyReceiverId) {
        Team team1 = findByCreator(String.valueOf(applyCreatorId));
        if (team1 == null) {
            throw new MyException(4, "team created by " + applyCreatorId + " does not exist");
        }
        Room room1 = roomService.findSelectedRoom(team1.getTeamId());
        if (room1 == null) {
            throw new MyException(4, "team created by " + applyCreatorId + " does not select any room");
        }
        JSONObject temp = new JSONObject();
        JSONObject tempRoom = new JSONObject();
        tempRoom.put("roomId", room1.getRoomId());
        tempRoom.put("buildingId", room1.getBuilding().getBuildingId());
        tempRoom.put("district", room1.getBuilding().getZone().getName());
        tempRoom.put("roomNumber", room1.getRoomNumber());
        tempRoom.put("floor", room1.getFloor());
        tempRoom.put("roomType", room1.getRoomType());
        tempRoom.put("gender", room1.getGender());
        tempRoom.put("selectedTeamCreatorId", room1.getSelectedTeam().getCreatorId());

        temp.put("applyRoom", tempRoom);
        temp.put(("applyTeamName"), team1.getTeamName());
        Notification notification = notificationService.createAndSaveNotification("roomExchange", applyReceiverId, temp.toJSONString());
        notificationService.save(notification);
        try {
            NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), applyReceiverId);
        } catch (Exception e) {
            throw new MyException(3, "websocket notification failed");
        }
    }
}
