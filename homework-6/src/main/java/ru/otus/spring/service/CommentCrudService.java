package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.jpa.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentCrudService implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void addComment(long bookId, String userName, String comment) {
        commentRepository.create(bookId, userName, comment);
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void updateComment(long commentId, String comment) {
        commentRepository.update(commentId, comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsForBook(long bookId) {
        return commentRepository.getByBook(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(String userName) {
        return commentRepository.getByUser(userName);
    }
}
