package cs309_dorm_backend.controller;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.domain.Comment;
import cs309_dorm_backend.domain.SecondComment;
import cs309_dorm_backend.dto.CommentDto;
import cs309_dorm_backend.dto.GlobalResponse;
import cs309_dorm_backend.dto.SecondCommentDto;
import cs309_dorm_backend.service.comment.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Service
@RestController
@RequestMapping("/comment")
@Api(tags = "Comment Controller")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/findAll")
    public List<Comment> findAllComments() {
        return commentService.findAllComments();
    }

    @GetMapping("/delete/{commentId}")
    public GlobalResponse deleteComment(@PathVariable int commentId) {
        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return GlobalResponse.builder()
                    .code(4)
                    .msg("Comment not found")
                    .data(null).build();
        } else {
            commentService.deleteComment(commentId);
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Comment deleted")
                    .data(comment).build();
        }
    }

    @GetMapping("/findCommentsByAuthorId/{authorId}")
    public List<Comment> findCommentsByAuthorId(@PathVariable int authorId) {
        return commentService.findCommentsByAuthorId(authorId);
    }

    @GetMapping("/findCommentsByRoomId/{roomId}")
    public List<Comment> findCommentsByRoomId(@PathVariable int roomId) {
        return commentService.findCommentsByRoomId(roomId);
    }

    @GetMapping("/findComment/{time}/{authorId}")
    public Comment findComment(@PathVariable Timestamp time, @PathVariable int authorId) {
        return commentService.findComment(time, authorId);
    }

    @PostMapping("/addComment")
    public GlobalResponse addComment(@RequestBody CommentDto commentDto) {
        Comment comment = commentService.addComment(commentDto);
        if (comment == null) {
            return GlobalResponse.builder()
                    .code(4)
                    .msg("Comment not added")
                    .data(null).build();
        } else {
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Comment added")
                    .data(comment).build();
        }
    }

    // second comment ============================================================

    @GetMapping("/findAllSecondComments")
    public List<SecondComment> findAllSecondComments() {
        return commentService.findAllSecondComments();
    }

    @GetMapping("/deleteSecondComment/{secondCommentId}")
    public GlobalResponse deleteSecondComment(@PathVariable int secondCommentId) {
        SecondComment secondComment = commentService.findSecondCommentById(secondCommentId);
        if (secondComment == null) {
            return GlobalResponse.builder()
                    .code(4)
                    .msg("Second comment not found")
                    .data(null).build();
        } else {
            commentService.deleteSecondComment(secondCommentId);
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Second comment deleted")
                    .data(secondComment).build();
        }
    }

    @GetMapping("/findSecondCommentsByAuthorId/{authorId}")
    public List<SecondComment> findSecondCommentsByAuthorId(@PathVariable int authorId) {
        return commentService.findSecondCommentsByAuthorId(authorId);
    }

    @GetMapping("/findSecondCommentsByParentId/{parentId}")
    public List<SecondComment> findSecondCommentsByParentId(@PathVariable int parentId) {
        return commentService.findSecondCommentsByParentId(parentId);
    }

    @GetMapping("/findSecondComment/{time}/{authorId}")
    public GlobalResponse findSecondComment(@PathVariable Timestamp time, @PathVariable int authorId) {
        SecondComment secondComment = commentService.findSecondComment(time, authorId);
        if (secondComment == null) {
            return GlobalResponse.builder()
                    .code(4)
                    .msg("Second comment not found")
                    .data(null).build();
        } else {
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Second comment found")
                    .data(secondComment).build();
        }
    }

    @PostMapping("/addSecondComment")
    public GlobalResponse addSecondComment(@RequestBody SecondCommentDto secondCommentDto) {
        SecondComment secondComment = commentService.addSecondComment(secondCommentDto);
        if (secondComment == null) {
            return GlobalResponse.builder()
                    .code(4)
                    .msg("Second comment not added")
                    .data(null).build();
        } else {
            return GlobalResponse.builder()
                    .code(0)
                    .msg("Second comment added")
                    .data(secondComment).build();
        }
    }


}
