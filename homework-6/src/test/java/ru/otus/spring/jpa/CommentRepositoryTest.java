package ru.otus.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository should ")
@DataJpaTest
@Import(CommentRepositoryJPA.class)
public class CommentRepositoryTest {

    private static final String UPDATED_TEXT = "updated comment";
    private static final Book BOOK_TWO = new Book(2L, "someTitle", "bla-bla-bla", new Author(2L, "NewAuthor"), new Genre(2L, "detective"));
    private static final Comment COMMENT_ONE = new Comment(1L, BOOK_TWO, "Petya", "text");
    private static final Comment COMMENT_TWO = new Comment(2L, BOOK_TWO, "Vasya", "bla-bla-bla");

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommentRepositoryJPA commentRepository;

    @Test
    @DisplayName("get all comments by book")
    public void getAllByBook() {
        final List<Comment> bookTwoComments = commentRepository.getByBook(BOOK_TWO.getId());
        assertThat(bookTwoComments)
                .hasSize(2)
                .flatExtracting(Comment::getId, Comment::getUser, Comment::getText)
                .containsExactly(
                        COMMENT_ONE.getId(), COMMENT_ONE.getUser(), COMMENT_ONE.getText(),
                        COMMENT_TWO.getId(), COMMENT_TWO.getUser(), COMMENT_TWO.getText()
                );
    }

    @Test
    @DisplayName("get comment by id")
    public void getById() {
        final Comment comment = commentRepository.getById(COMMENT_ONE.getId());
        assertThat(comment)
                .extracting(Comment::getId, Comment::getUser, Comment::getText)
                .containsExactly(COMMENT_ONE.getId(), COMMENT_ONE.getUser(), COMMENT_ONE.getText());
    }

    @Test
    @DisplayName("delete comment")
    public void delete() {
        commentRepository.deleteById(COMMENT_ONE.getId());
        final Comment deleted = entityManager.getEntityManager().find(Comment.class, COMMENT_ONE.getId());
        assertThat(deleted).isNull();
    }

    @Test
    @DisplayName("update comment")
    public void update() {
        commentRepository.update(COMMENT_ONE.getId(), UPDATED_TEXT);

        final Comment updated = entityManager.getEntityManager().find(Comment.class, COMMENT_ONE.getId());
        assertThat(updated)
                .extracting(Comment::getText)
                .isEqualTo(UPDATED_TEXT);
    }

    @Test
    @DisplayName("add comment")
    public void insertComment() {
        final String userName = "userName";
        final String newText = "newText";
        commentRepository.create(BOOK_TWO.getId(), userName, newText);

        final TypedQuery<Comment> commentsQuery = entityManager.getEntityManager().createQuery("select c from Comment c where c.user = '" + userName + "'", Comment.class);
        final List<Comment> newComment = commentsQuery.getResultList();
        assertThat(newComment)
                .hasSize(1)
                .element(0)
                .extracting(comment -> comment.getBook().getId(), Comment::getText)
                .containsExactly(BOOK_TWO.getId(), newText);
    }

}
