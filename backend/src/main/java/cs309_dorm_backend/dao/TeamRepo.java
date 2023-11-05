package cs309_dorm_backend.dao;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {
    List<Team> findAll();

    @Query(value = "select * from teams where creator_id = ?1", nativeQuery = true)
    Team findByCreator(int creatorId);

    @Modifying
    @Query(value = "delete from teams where creator_id = :creatorId", nativeQuery = true)
    void deleteByCreator(int creatorId);

    @Modifying
    @Transactional
    @Query(value = "update students set team_id = :teamId where student_id = :studentId", nativeQuery = true)
    void setTeam(int studentId, int teamId);

    @Modifying
    @Transactional
    @Query(value = "update students set team_id = null where student_id = :studentId", nativeQuery = true)
    void removeStudentTeam(int studentId);
}
