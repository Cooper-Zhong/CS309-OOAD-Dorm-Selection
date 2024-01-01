package cs309_dorm_backend.service.invitation;

import com.alibaba.fastjson.JSON;
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
import cs309_dorm_backend.websocket.NotificationWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
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
    public void rejectInvitation(InvitationDto invitationDto) {
        String studentId = invitationDto.getStudentId();
        String creatorId = invitationDto.getCreatorId();
        boolean isInvitation = invitationDto.isInvitation();
        Notification notification;
        if (isInvitation) {
            notification = notificationService.createAndSaveNotification("system", creatorId, "Student " + studentId + " rejected your invitation");
            try {
                NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), creatorId);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new MyException(1, "websocket notification failed");
            }
        } else {
            notification = notificationService.createAndSaveNotification("system", studentId, "Team by creator " + creatorId + " rejected your application");
            try {
                NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), studentId);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new MyException(2, "websocket notification failed");
            }
        }
    }

    @Override
    @Transactional
    public Invitation addInvitation(InvitationDto invitationDto) {
        try {
            Invitation invitation = save(convertToInvitation(invitationDto));
            JSONObject temp = new JSONObject();
            Notification notification;
            if (invitationDto.isInvitation()) { // invitation
                temp.put("teamName", invitation.getTeam().getTeamName());
                temp.put("timestamp", new Timestamp(System.currentTimeMillis()));
                notification = notificationService.createAndSaveNotification("invitation", invitationDto.getStudentId(), temp.toJSONString());
                log.debug("debug: " + temp.toJSONString());
                System.out.println((JSON.toJSONString(notification)));
                try {
                    NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), invitationDto.getStudentId());
                } catch (Exception e) {
                    log.info(e.getMessage());
                    throw new MyException(2, "websocket notification failed");
                }
            } else { // application, send to team creator
                temp.put("senderName", invitation.getStudent().getName());
                notification = notificationService.createAndSaveNotification("application", invitationDto.getCreatorId(), temp.toJSONString());
                try {
                    NotificationWebSocketServer.sendData(JSON.toJSONString(notificationService.toDto(notification)), invitationDto.getCreatorId());
                } catch (Exception e) {
                    log.info(e.getMessage());
                    throw new MyException(3, "websocket notification failed");
                }
            }
            return invitation;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException(4, e.getMessage());
        }
    }

    @Override
    public Invitation save(Invitation invitation) {
        return invitationRepo.save(invitation);
    }

    private Invitation convertToInvitation(InvitationDto invitationDto) {
        Student creator = studentService.findById(invitationDto.getCreatorId());
        if (creator == null) {
            String msg = "Creator" + invitationDto.getCreatorId() + " not found. ";
            msg += "Please first create a team and then invite student to join the team.";
            throw new MyException(4, msg);
        }
        Team team = teamService.findByCreator(creator.getStudentId());
        if (team == null) {
            throw new MyException(5, "team not found");
        }
        Student student = studentService.findById(invitationDto.getStudentId());
        if (student == null) {
            throw new MyException(6, "student " + invitationDto.getStudentId() + " not found");
        }

        Invitation old = invitationRepo.findInvitation(team.getTeamId(), student.getStudentId());
        if (old != null) {
            if (old.isInvitation() && invitationDto.isInvitation()) {
                throw new MyException(7, "You have already invited student " + student.getStudentId() + " to join your team");
            }
            if (!old.isInvitation() && !invitationDto.isInvitation()) {
                throw new MyException(8, "You have already applied to join team of creator " + creator.getStudentId());
            }
            if (old.isInvitation() && !invitationDto.isInvitation()) {
                throw new MyException(9, "The team has already invited you to join. Please accept the invitation.");
            }
            throw new MyException(10, "Student " + student.getStudentId() + " has already applied to join your team. Please accept the application.");
        }
        return Invitation.builder()
                .team(team)
                .student(student)
                .isInvitation(invitationDto.isInvitation())
                .build();
    }
}
