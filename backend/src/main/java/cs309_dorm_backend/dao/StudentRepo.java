package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface StudentRepo extends JpaRepository<Student, Integer> {


    @Modifying
    @Query(value = "update students set team_id = null where student_id = :studentId", nativeQuery = true)
    void removeTeam(int studentId);

}
