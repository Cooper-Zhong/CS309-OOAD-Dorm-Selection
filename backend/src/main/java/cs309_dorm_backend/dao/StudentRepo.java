package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.Student;
import cs309_dorm_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Component
public interface StudentRepo extends JpaRepository<Student, String> {


    @Modifying
    @Query(value = "update students set team_id = null where student_id = :studentId", nativeQuery = true)
    void removeTeam(String studentId);

    @Modifying
    @Transactional
    @Query(value = "delete from students where student_id = :studentId", nativeQuery = true)
    void deleteStudent(String studentId);

    @Query(value = "select name from students where student_id = :studentId", nativeQuery = true)
    String findNameById(String studentId);

}
