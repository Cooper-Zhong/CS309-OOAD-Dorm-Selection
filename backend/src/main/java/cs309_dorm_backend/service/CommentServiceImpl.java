package cs309_dorm_backend.service;

import cs309_dorm_backend.domain.Comment;
import cs309_dorm_backend.dao.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo CommentRepo;

    @Override
    public void deleteById(long id) {
        CommentRepo.deleteById(id);
    }

    @Override
    public Comment save(Comment Comment) {
        return null;
    }

    public List<Comment> findAll() {
        return CommentRepo.findAll();
    }

}
