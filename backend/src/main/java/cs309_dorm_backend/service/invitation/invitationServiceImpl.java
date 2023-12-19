package cs309_dorm_backend.service.invitation;

import com.alibaba.fastjson.JSONObject;
import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.InvitationRepo;
import cs309_dorm_backend.domain.Invitation;
import cs309_dorm_backend.domain.Notification;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.InvitationDto;
import cs309_dorm_backend.service.notification.NotificationService;
import cs309_dorm_backend.service.student.StudentService;
import cs309_dorm_backend.service.team.TeamService;
import cs309_dorm_backend.websocket.WebSocketServer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Log
public class invitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Invitation> findTeamRelated(int teamId) {
        return invitationRepo.findTeamRelated(teamId);
    }

    @Override
    public List<Invitation> findStudentRelated(String studentId) {
        return invitationRepo.findStudentRelated(studentId);
    }


    // always return tru
    @Override
    public boolean deleteInvitation(InvitationDto invitationDto) {
        try {
            Team team = teamService.findByCreator(invitationDto.getCreatorId());
            if (team == null) {
                throw new MyException(1, "team not found");
            }
            invitationRepo.deleteInvitation(team.getTeamId(), invitationDto.getStudentId(), invitationDto.isInvitation());
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public Invitation addInvitation(InvitationDto invitationDto) {
        try {
            Invitation invitation = save(convertToInvitation(invitationDto));
            JSONObject temp = new JSONObject();
            temp.put("teamName", invitation.getTeam().getTeamName());
            temp.put("timestamp", new Timestamp(System.currentTimeMillis()));
            Notification notification;
            if (invitationDto.isInvitation()) { // invitation
                notification = notificationService.createNotification("invitation", invitationDto.getStudentId(), temp.toJSONString());
                WebSocketServer.sendData((JSONObject) JSONObject.toJSON(notification), invitationDto.getStudentId());
            } else { // application, send to team creator
                notification = notificationService.createNotification("invitation", invitationDto.getCreatorId(), temp.toJSONString());
                WebSocketServer.sendData((JSONObject) JSONObject.toJSON(notification), invitationDto.getCreatorId());
            }
            return invitation;
        } catch (Exception e) {
            throw new MyException(4, "invitation failed");
        }
    }

    @Override
    public Invitation save(Invitation invitation) {
        return invitationRepo.save(invitation);
    }

    private Invitation convertToInvitation(InvitationDto invitationDto) {
        Student creator = studentService.findById(invitationDto.getCreatorId());
        if (creator == null) {
            throw new MyException(4, "creator" + invitationDto.getCreatorId() + " not found");
        }
        Team team = teamService.findByCreator(creator.getStudentId());
        if (team == null) {
            throw new MyException(5, "team not found");
        }
        Student student = studentService.findById(invitationDto.getStudentId());
        if (student == null) {
            throw new MyException(6, "student " + invitationDto.getStudentId() + " not found");
        }
        return Invitation.builder()
                .team(team)
                .student(student)
                .isInvitation(invitationDto.isInvitation())
                .build();
    }
}
