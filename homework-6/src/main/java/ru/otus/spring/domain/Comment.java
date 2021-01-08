package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "user")
    private String user;

    @Column(name = "text")
    private String text;
}
