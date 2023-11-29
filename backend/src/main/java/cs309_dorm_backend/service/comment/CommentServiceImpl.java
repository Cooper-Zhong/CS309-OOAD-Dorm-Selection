package cs309_dorm_backend.service.comment;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dao.SecondCommentRepo;
import cs309_dorm_backend.domain.Comment;
import cs309_dorm_backend.dao.CommentRepo;
import cs309_dorm_backend.domain.Room;
import cs309_dorm_backend.domain.SecondComment;
import cs309_dorm_backend.domain.User;
import cs309_dorm_backend.dto.CommentDto;
import cs309_dorm_backend.dto.SecondCommentDto;
import cs309_dorm_backend.service.room.RoomService;
import cs309_dorm_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private SecondCommentRepo secondCommentRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    @Override
    public Comment findCommentById(int commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }


    @Override
    public List<Comment> findAllComments() {
        return commentRepo.findAll();
    }


    @Override
    @Transactional
    public Comment deleteComment(int commentId) {
        Comment comment = commentRepo.findById(commentId).orElse(null);
        if (comment != null) {
            Set<SecondComment> secondComments = comment.getSecondComments();
            secondCommentRepo.deleteAll(secondComments); // delete all second comments, remove reference
            commentRepo.delete(comment);
            return comment;
        } else {
            return null;
        }
    }

    @Override
    public Comment saveComment(Comment Comment) {
        return commentRepo.save(Comment);
    }

    @Override
    public Comment findComment(Timestamp time, String authorId) {
        if (userService.findByCampusId(authorId) == null) {
            throw new MyException(4, "user " + authorId + " does not exist");
        }
        return commentRepo.findComment(time, authorId);
    }

    @Override
    public List<Comment> findCommentsByAuthorId(String authorId) {
        if (userService.findByCampusId(authorId) == null) {
            throw new MyException(4, "user " + authorId + " does not exist");
        }
        return commentRepo.findCommentsByAuthorId(authorId);
    }

    @Override
    public List<Comment> findCommentsByRoomId(int roomId) {
        if (roomService.findById(roomId) == null) {
            throw new MyException(5, "room " + roomId + " does not exist");
        }
        return commentRepo.findCommentsByRoomId(roomId);
    }

    @Override
    public Comment addComment(CommentDto commentDto) {
        User author = userService.findByCampusId(commentDto.getAuthorId());
        if (author == null) {
            throw new MyException(4, "user " + commentDto.getAuthorId() + " does not exist");
        }
        Room room = roomService.findOne(commentDto.getBuildingId(), commentDto.getRoomNumber());
        if (room == null) {
            throw new MyException(5, "room " + commentDto.getBuildingId() + "-" + commentDto.getRoomNumber() + " does not exist");
        }
        return saveComment(convertToComment(commentDto));
    }

    // second comment ============================================================
    @Override
    public SecondComment findSecondCommentById(int secondCommentId) {
        return secondCommentRepo.findById(secondCommentId).orElse(null);
    }

    @Override
    public List<SecondComment> findAllSecondComments() {
        return secondCommentRepo.findAll();
    }

    @Override
    public SecondComment deleteSecondComment(int secondCommentId) {
        SecondComment secondComment = secondCommentRepo.findById(secondCommentId).orElse(null);
        if (secondComment != null) {
            secondCommentRepo.delete(secondComment);
            return secondComment;
        } else {
            return null;
        }
    }

    @Override
    public SecondComment saveSecondComment(SecondComment secondComment) {
        return secondCommentRepo.save(secondComment);
    }

    @Override
    public SecondComment findSecondComment(Timestamp time, String authorId) {
        return secondCommentRepo.findSecondComment(time, authorId);
    }

    @Override
    public List<SecondComment> findSecondCommentsByAuthorId(String authorId) {
        if (userService.findByCampusId(authorId) == null) {
            throw new MyException(4, "user " + authorId + " does not exist");
        }
        return secondCommentRepo.findSecondCommentsByAuthorId(authorId);
    }

    @Override
    public List<SecondComment> findSecondCommentsByParentId(int parentId) {
        if (commentRepo.findById(parentId).orElse(null) == null) {
            throw new MyException(5, "parent comment " + parentId + " does not exist");
        }
        return secondCommentRepo.findSecondCommentsByParentId(parentId);
    }

    @Override
    public SecondComment addSecondComment(SecondCommentDto secondCommentDto) {
        User author = userService.findByCampusId(secondCommentDto.getAuthorId());
        if (author == null) {
            throw new MyException(4, "user " + secondCommentDto.getAuthorId() + " does not exist");
        }
        Comment parent = commentRepo.findById(secondCommentDto.getParentId()).orElse(null);
        if (parent == null) {
            throw new MyException(5, "parent comment " + secondCommentDto.getParentId() + " does not exist");
        }
        return saveSecondComment(convertToSecondComment(secondCommentDto));
    }

    private Comment convertToComment(CommentDto commentDto) {
        User author = userService.findByCampusId(commentDto.getAuthorId());
        Room room = roomService.findOne(commentDto.getBuildingId(), commentDto.getRoomNumber());
        return Comment.builder().author(author)
                .room(room)
                .content(commentDto.getContent())
                .time(new Timestamp(System.currentTimeMillis())) // current time
                .build();
    }

    private SecondComment convertToSecondComment(SecondCommentDto secondCommentDto) {
        User author = userService.findByCampusId(secondCommentDto.getAuthorId());
        Comment parent = commentRepo.findById(secondCommentDto.getParentId()).orElse(null);
        return SecondComment.builder().author(author)
                .parentComment(parent)
                .content(secondCommentDto.getContent())
                .time(new Timestamp(System.currentTimeMillis())) // current time
                .build();
    }


}
