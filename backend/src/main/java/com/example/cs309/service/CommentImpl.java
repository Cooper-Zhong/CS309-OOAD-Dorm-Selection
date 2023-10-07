package com.example.cs309.service;

import com.example.cs309.api.CommentRepos;
import com.example.cs309.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentImpl implements CommentService {
    @Autowired
    private CommentRepos CommentRepos;

    @Override
    public void deleteById(long id) {
        CommentRepos.deleteById(id);
    }

    @Override
    public Comment save(Comment Comment) {
        return null;
    }

    public List<Comment> findAll(){
        return CommentRepos.findAll();
    }

}
