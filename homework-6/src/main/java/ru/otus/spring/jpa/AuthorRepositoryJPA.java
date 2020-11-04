package ru.otus.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryJPA implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> getAll() {
        final TypedQuery<Author> authorsQuery = em.createQuery(
                "select a from authors a",
                Author.class
        );
        return authorsQuery.getResultList();
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        final var select = em.createQuery("select a from authors a where a.name = : name", Author.class);
        select.setParameter("name", name);
        return select.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteById(long id) {
        final var delete = em.createQuery("delete from authors a where a.id = : id");
        delete.setParameter("id", id);
        delete.executeUpdate();
    }
}
