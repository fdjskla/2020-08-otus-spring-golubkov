DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS COMMENTS;

CREATE TABLE BOOKS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, TITLE VARCHAR(255), TEXT CLOB, AUTHOR_ID BIGINT, GENRE_ID BIGINT);
CREATE TABLE AUTHORS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));
CREATE INDEX AUTHOR_NAME ON AUTHORS(NAME);
CREATE TABLE GENRES(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));
CREATE INDEX GENRE_NAME ON GENRES(NAME);
CREATE TABLE COMMENTS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, USER VARCHAR(255), TEXT CLOB, BOOK_ID BIGINT);
CREATE INDEX USER_NAME ON COMMENTS(USER);

ALTER TABLE BOOKS ADD FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID);
ALTER TABLE BOOKS ADD FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID);
ALTER TABLE COMMENTS ADD FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID);