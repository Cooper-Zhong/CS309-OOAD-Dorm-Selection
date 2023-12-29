package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Integer> {

    @Query(value = "select * from messages where receiver_id = ?1", nativeQuery = true)
    List<Message> findByReceiverId(String receiverId);

    @Query(value = "select * from messages where sender_id = ?1 or receiver_id = ?2", nativeQuery = true)
    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

    @Modifying
    @Query(value = "delete from messages where receiver_id = ?1", nativeQuery = true)
    void deleteByReceiverId(String receiverId);

}
