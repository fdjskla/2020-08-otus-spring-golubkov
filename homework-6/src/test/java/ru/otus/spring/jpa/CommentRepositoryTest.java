package ru.otus.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepository should ")
@DataJpaTest
@Import(CommentRepositoryJPA.class)
public class CommentRepositoryTest {

    private static final String UPDATED_TEXT = "updated comment";
    private static Book bookTwo = new Book(2L, "someTitle", "bla-bla-bla", new Author(2L, "NewAuthor"), new Genre(2L, "detective"), List.of());
    private static final Comment COMMENT_ONE = new Comment(1L, bookTwo, "Petya", "text");
    private static final Comment COMMENT_TWO = new Comment(2L, bookTwo, "Vasya", "bla-bla-bla");

    static {
        bookTwo.setComments(List.of(COMMENT_ONE, COMMENT_TWO));
    }

    @Autowired
    private CommentRepositoryJPA commentRepository;

    @Test
    @DisplayName("get all comments by book")
    public void getAll() {
        final List<Comment> bookTwoComments = commentRepository.getByBook(bookTwo.getId());
        assertThat(bookTwoComments)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(COMMENT_ONE, COMMENT_TWO);
    }

    @Test
    @DisplayName("get comment by id")
    public void getById() {
        final Comment comment = commentRepository.getById(COMMENT_ONE.getId());
        assertThat(comment)
                .usingRecursiveComparison()
                .isEqualTo(COMMENT_ONE);
    }

    @Test
    @DisplayName("delete comment")
    public void delete() {
        commentRepository.deleteById(COMMENT_ONE.getId());
        final Comment deleted = commentRepository.getById(COMMENT_ONE.getId());
        assertThat(deleted).isNull();
    }

    @Test
    @DisplayName("update comment")
    public void update() {
        commentRepository.update(COMMENT_ONE.getId(), UPDATED_TEXT);

        assertThat(commentRepository.getById(COMMENT_ONE.getId()))
                .extracting(Comment::getText)
                .isEqualTo(UPDATED_TEXT);
    }

    @Test
    @DisplayName("add comment")
    public void insertBook() {
        final String userName = "userName";
        final String newText = "newText";
        commentRepository.create(bookTwo.getId(), userName, newText);

        final List<Comment> newComment = commentRepository.getByUser(userName);
        assertThat(newComment)
                .hasSize(1)
                .element(0)
                .extracting(comment -> comment.getBook().getId(), Comment::getText)
                .containsExactly(bookTwo.getId(), newText);
    }

}
