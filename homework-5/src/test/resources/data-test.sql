insert into authors (`name`) values ('Author1');
insert into authors (`name`) values ('NewAuthor');

insert into genres (`name`) values('romance');
insert into genres (`name`) values('detective');

insert into books(`title`, `text`, `author_id`, `genre_id`) values('bookTitle', 'text', 1, 1);
insert into books(`title`, `text`, `author_id`, `genre_id`) values('someTitle', 'bla-bla-bla', 2, 2);