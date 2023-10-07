package com.example.cs309.service;

import com.example.cs309.domain.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> findAll();
    public void deleteById(long id);
    public Comment save(Comment comment);
}
