package com.example.cs309.api;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cs309.domain.Comment;
import java.util.List;
public interface CommentRepos extends JpaRepository<Comment,Long> {
    public List<Comment> findAll();
}
