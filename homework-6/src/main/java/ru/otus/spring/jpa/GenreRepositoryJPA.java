package ru.otus.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreRepositoryJPA implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> getAll() {
        final TypedQuery<Genre> genresQuery = em.createQuery(
                "select g from genre g",
                Genre.class
        );
        return genresQuery.getResultList();
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        final var select = em.createQuery("select g from genres g where g.name = : name", Genre.class);
        select.setParameter("name", name);
        return select.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void deleteById(long id) {
        final var delete = em.createQuery("delete from genres g where g.id = : id");
        delete.setParameter("id", id);
        delete.executeUpdate();
    }
}
