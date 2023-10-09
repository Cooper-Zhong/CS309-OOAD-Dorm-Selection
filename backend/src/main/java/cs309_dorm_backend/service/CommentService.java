package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> findAll();
    public void deleteById(long id);
    public Comment save(Comment comment);
}
