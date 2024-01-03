package cs309_dorm_backend.service.invitation;

import cs309_dorm_backend.domain.Invitation;
import cs309_dorm_backend.dto.InvitationDto;

import java.util.List;

public interface InvitationService {

    //查看一个 team 的所有申请+邀请
    List<Invitation> findTeamRelated(int teamId);

    //查看一个 student 的所有申请+邀请
    List<Invitation> findStudentRelated(String studentId);

    boolean deleteInvitation(InvitationDto invitationDto);

    Invitation addInvitation(InvitationDto invitationDto);

    Invitation save(Invitation invitation);

//    // team 邀请一个 student 加入 team
//    void sendInvitation(String teamId, String studentId);
//
//    // student 申请加入 team
//    void sendApplication(String teamId, String studentId);
//
//    //student 接受team的邀请
//    void acceptInvitation(String teamId, String studentId);
//
//    //team 接受student的申请
//    void acceptApplication(String teamId, String studentId);
//
//    //student 拒绝team的邀请
//    void rejectInvitation(String teamId, String studentId);
//
//    //team 拒绝student的申请
//    void rejectApplication(String teamId, String studentId);
//
//    //student 取消申请
//    void cancelInvitation(String teamId, String studentId);
//
//    //team 取消邀请
//    void cancelApplication(String teamId, String studentId);
}
