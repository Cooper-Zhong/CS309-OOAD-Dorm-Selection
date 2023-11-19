package cs309_dorm_backend.dao;

import cs309_dorm_backend.domain.SecondComment;
import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Comment;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface SecondCommentRepo extends JpaRepository<SecondComment, Integer> {
    public List<SecondComment> findAll();

    @Query(value = "select * from second_comments where time = :time and author_id = :authorId", nativeQuery = true)
    public SecondComment findSecondComment(Timestamp time, int authorId);

    @Query(value = "select * from second_comments where author_id = :authorId", nativeQuery = true)
    public List<SecondComment> findSecondCommentsByAuthorId(int authorId);

    @Query(value = "select * from second_comments where parent_comment_id = :parentId", nativeQuery = true)
    public List<SecondComment> findSecondCommentsByParentId(int parentId);
}
