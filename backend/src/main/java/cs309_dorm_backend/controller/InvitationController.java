package cs309_dorm_backend.controller;

import cs309_dorm_backend.domain.Invitation;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.InvitationDto;
import cs309_dorm_backend.service.invitation.InvitationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;


    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/findTeamRelated/{teamId}")
    public GlobalResponse findTeamRelated(@PathVariable int teamId) {
        List<Invitation> invitations = invitationService.findTeamRelated(teamId);
        if (invitations == null) {
            return new GlobalResponse<>(1, "invitation not found", null);
        } else {
            return new GlobalResponse<>(0, "success", invitations);
        }
    }

    @GetMapping("/findStudentRelated/{studentId}")
    public GlobalResponse findStudentRelated(@PathVariable String studentId) {
        List<Invitation> invitations = invitationService.findStudentRelated(studentId);
        if (invitations == null) {
            return new GlobalResponse<>(1, "invitation not found", null);
        } else {
            return new GlobalResponse<>(0, "success", invitations);
        }
    }

    @PostMapping("/addInvitation")
    public GlobalResponse addInvitation(@RequestBody InvitationDto invitationDto) {
        Invitation invitation = invitationService.addInvitation(invitationDto);
        if (invitation == null) {
            return new GlobalResponse<>(1, "invitation not found", null);
        } else {
            if (invitation.isInvitation()) {
                return new GlobalResponse<>(0, "invite student " + invitation.getStudent().getStudentId()+ " successfully", invitation);
            } else {
                return new GlobalResponse<>(0, "apply to join team of creator " + invitation.getTeam().getCreatorId() + " successfully", invitation);
            }
        }
    }

    @PostMapping("/deleteInvitation")
    @Transactional
    public GlobalResponse deleteInvitation(@RequestBody InvitationDto invitationDto) {
        boolean result = invitationService.deleteInvitation(invitationDto);
        if (result) {
            return new GlobalResponse<>(0, "delete success", null);
        } else {
            return new GlobalResponse<>(1, "invitation not found", null);
        }
    }
}
