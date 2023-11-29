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
    Team findByCreator(String creatorId);

    @Modifying
    @Query(value = "delete from teams where creator_id = :creatorId", nativeQuery = true)
    void deleteByCreator(String creatorId);

    @Modifying
    @Transactional
    @Query(value = "update students set team_id = :teamId where student_id = :studentId", nativeQuery = true)
    void setTeam(String studentId, int teamId);

    @Modifying
    @Transactional
    @Query(value = "update teams set creator_id = :newCreatorId where creator_id = :oldCreatorId", nativeQuery = true)
    void updateTeamCreator(String oldCreatorId, String newCreatorId);

    @Modifying
    @Transactional
    @Query(value = "update students set team_id = null where student_id = :studentId", nativeQuery = true)
    void removeStudentTeam(String studentId);

    @Modifying
    @Transactional
    @Query(value = "insert into favorite_rooms (team_id, room_id) values (:teamId, :roomId)", nativeQuery = true)
    void addFavoriteRoom(int teamId, int roomId);

    @Modifying
    @Transactional
    @Query(value = "delete from favorite_rooms where team_id = :teamId and room_id = :roomId", nativeQuery = true)
    void removeFavoriteRoom(int teamId, int roomId);

}
