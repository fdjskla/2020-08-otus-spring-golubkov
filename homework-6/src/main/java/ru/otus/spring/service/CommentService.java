package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    void addComment(long bookId, String userName, String comment);

    void deleteComment(long commentId);

    void updateComment(long commentId, String comment);

    List<Comment> getCommentsForBook(long bookId);

    List<Comment> getCommentsByUser(String userName);
}
