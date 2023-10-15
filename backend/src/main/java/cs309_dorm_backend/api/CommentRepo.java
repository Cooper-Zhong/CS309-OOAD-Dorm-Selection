package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;
import cs309_dorm_backend.domain.Comment;
import java.util.List;
public interface CommentRepo extends JpaRepository<Comment,Long> {
    public List<Comment> findAll();
}
