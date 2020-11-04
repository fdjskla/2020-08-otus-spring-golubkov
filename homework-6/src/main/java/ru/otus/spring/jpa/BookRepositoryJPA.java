package ru.otus.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAll() {
        final EntityGraph<?> booksEntityGraph = em.getEntityGraph("books-entity-graph");

        final TypedQuery<Book> booksQuery = em.createQuery(
                "select b from Book b join fetch b.author join fetch b.genre",
                Book.class
        );

        booksQuery.setHint("javax.persistence.fetchgraph", booksEntityGraph);

        return booksQuery.getResultList();
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null || book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        final var delete = em.createQuery("delete from Book b where b.id = : id");
        delete.setParameter("id", id);
        delete.executeUpdate();
    }

}
