package cs309_dorm_backend.service.comment;

import cs309_dorm_backend.domain.Comment;
import cs309_dorm_backend.domain.SecondComment;
import cs309_dorm_backend.dto.CommentDto;
import cs309_dorm_backend.dto.SecondCommentDto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface CommentService {

    Comment findCommentById(int commentId);
    // comment
    List<Comment> findAllComments();

    Comment deleteComment(int commentId);

    Comment saveComment(Comment comment);

    Comment findComment(Timestamp time, int authorId);

    List<Comment> findCommentsByAuthorId(int authorId);

    List<Comment> findCommentsByRoomId(int roomId);

    Comment addComment(CommentDto commentDto);

    // second comment

    SecondComment findSecondCommentById(int secondCommentId);

    List<SecondComment> findAllSecondComments();

    SecondComment deleteSecondComment(int secondCommentId);

    SecondComment saveSecondComment(SecondComment secondComment);

    SecondComment findSecondComment(Timestamp time, int authorId);

    List<SecondComment> findSecondCommentsByAuthorId(int authorId);

    List<SecondComment> findSecondCommentsByParentId(int parentId);

    SecondComment addSecondComment(SecondCommentDto secondCommentDto);
}
