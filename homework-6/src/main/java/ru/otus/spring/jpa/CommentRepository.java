package ru.otus.spring.jpa;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment getById(long id);

    List<Comment> getByUser(String userName);

    List<Comment> getByBook(long bookId);

    Comment save(Comment comment);

    void create(long bookId, String userName, String text);

    void update(long commentId, String updatedText);

    void deleteById(long id);
}
