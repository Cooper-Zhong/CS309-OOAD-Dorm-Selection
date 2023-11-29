package cs309_dorm_backend.service.invitation;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.InvitationRepo;
import cs309_dorm_backend.domain.Invitation;
import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.Team;
import cs309_dorm_backend.dto.InvitationDto;
import cs309_dorm_backend.service.student.StudentService;
import cs309_dorm_backend.service.team.TeamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            invitationRepo.deleteInvitation(invitationDto.getTeamId(), invitationDto.getStudentId(), invitationDto.isInvitation());
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    public Invitation addInvitation(InvitationDto invitationDto) {
        Invitation invitation = convertToInvitation(invitationDto);
        try {
            return invitationRepo.save(invitation);
        } catch (Exception e) {
            throw new MyException(1, "invitation already exists");
        }
    }

    @Override
    public Invitation save(Invitation invitation) {
        return invitationRepo.save(invitation);
    }

    private Invitation convertToInvitation(InvitationDto invitationDto) {
        Team team = teamService.findById(invitationDto.getTeamId());
        if (team == null) {
            throw new MyException(1, "team not found");
        }
        Student student = studentService.findById(invitationDto.getStudentId());
        if (student == null) {
            throw new MyException(1, "student not found");
        }
        return Invitation.builder()
                .team(team)
                .student(student)
                .isInvitation(invitationDto.isInvitation())
                .build();

    }


}
