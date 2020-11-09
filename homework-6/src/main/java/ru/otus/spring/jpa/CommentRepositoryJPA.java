package ru.otus.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepositoryJPA implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByUser(String userName) {
        final var select = em.createQuery("select c from Comment c where c.user = : user", Comment.class);
        select.setParameter("user", userName);
        return select.getResultList();
    }

    @Override
    public List<Comment> getByBook(long bookId) {
        final var select = em.createNativeQuery("select c.id, c.book_id, c.user, c.text from comments c where c.book_id = ? ", Comment.class);
        select.setParameter(1, bookId);
        return select.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void create(long bookId, String userName, String text) {
        final var insert = em.createNativeQuery("insert into comments (book_id, user, text) " +
                "values(?,?,?)");
        insert.setParameter(1, bookId);
        insert.setParameter(2, userName);
        insert.setParameter(3, text);
        insert.executeUpdate();
    }

    @Override
    public void update(long commentId, String updatedText) {
        final var update = em.createQuery("update Comment c set text = : text where c.id = : id");
        update.setParameter("text", updatedText);
        update.setParameter("id", commentId);
        update.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        final var delete = em.createQuery("delete from Comment c where c.id = : id");
        delete.setParameter("id", id);
        delete.executeUpdate();
    }
}
