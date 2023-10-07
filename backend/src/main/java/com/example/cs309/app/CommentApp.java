package com.example.cs309.app;

import com.example.cs309.domain.Comment;
import com.example.cs309.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //类控制器
@RequestMapping("/com")
public class CommentApp {
    @Autowired
    private CommentService commentService;

    @GetMapping("/record")
    public List<Comment> findAll(){
        System.out.println(commentService.getClass().getName());
        return commentService.findAll();
    }

    @PostMapping("/record")
    public Comment addOne(Comment comment){
        return commentService.save(comment);
    }

    //use requestparam to update a line
    @PutMapping("/record")
    public Comment update(@RequestParam long id,
                          @RequestParam long student_id,
                            @RequestParam long room_id,
                            @RequestParam String comment_text,
                            @RequestParam long parent_comment_id
                          ){
        Comment comment=new Comment();
        comment.setId(id);
        comment.setParent_comment_id(parent_comment_id);
        comment.setComment_text(comment_text);
        comment.setRoom_id(room_id);
        comment.setStudent_id(student_id);

        return commentService.save(comment);
    }

    @DeleteMapping("record/{id}")
    public void deleteOne(@PathVariable long id){
        commentService.deleteById(id);
    }

}
