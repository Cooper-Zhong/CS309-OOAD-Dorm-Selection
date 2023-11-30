package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Message;
public interface MessageRepo extends JpaRepository<Message, Integer> {

}
