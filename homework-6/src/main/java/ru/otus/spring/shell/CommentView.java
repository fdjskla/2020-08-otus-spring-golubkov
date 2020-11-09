package ru.otus.spring.shell;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentView {

    private final String userName;
    private final String comment;

    public String toShellView() {
        return "CommentView{" +
                "userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
