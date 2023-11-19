package cs309_dorm_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Comment;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    public List<Comment> findAll();

    @Query(value = "select * from comments where time = :time and author_id = :authorId", nativeQuery = true)
    public Comment findComment(Timestamp time, int authorId);

    @Query(value = "select * from comments where author_id = :authorId", nativeQuery = true)
    public List<Comment> findCommentsByAuthorId(int authorId);

    @Query(value = "select * from comments where room_id = :roomId", nativeQuery = true)
    public List<Comment> findCommentsByRoomId(int roomId);

}
