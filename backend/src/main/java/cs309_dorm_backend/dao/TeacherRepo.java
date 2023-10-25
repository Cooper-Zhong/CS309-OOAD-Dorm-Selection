package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs309_dorm_backend.domain.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
}
