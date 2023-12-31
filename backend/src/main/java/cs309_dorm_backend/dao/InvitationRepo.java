package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepo extends JpaRepository<Invitation, Integer> {

    @Query(value = "select * from invitations where team_id = ?1", nativeQuery = true)
    List<Invitation> findTeamRelated(int teamId);

    @Query(value = "select * from invitations where student_id = ?1", nativeQuery = true)
    List<Invitation>findStudentRelated(String studentId);

    @Modifying
    @Query(value = "delete from invitations where team_id = ?1 and student_id = ?2 and is_invitation = ?3", nativeQuery = true)
    void deleteInvitation(int teamId, String studentId, boolean isInvitation);

    @Query(value = "select * from invitations where team_id = ?1 and student_id = ?2", nativeQuery = true)
    Invitation findInvitation(int teamId, String studentId);


}
