insert into authors (`name`) values ('Alexander Pushkin');
insert into authors (`name`) values ('Leo Tolstoy');
insert into authors (`name`) values ('Fedor Dostoevsky');
insert into authors (`name`) values ('Ivan Turgenev');
insert into authors (`name`) values ('Vladimir Mayakovsky');

insert into genres (`name`) values('romance');
insert into genres (`name`) values('detective');
insert into genres (`name`) values('horror');
insert into genres (`name`) values('adventure');
insert into genres (`name`) values('tale');
insert into genres (`name`) values('science fiction');
insert into genres (`name`) values('nonfiction');
insert into genres (`name`) values('biography');

insert into books(`title`, `text`, `author_id`, `genre_id`) values('title', 'text', 1, 1);
insert into books(`title`, `text`, `author_id`, `genre_id`) values('title1', 'text1', 2, 2);
insert into books(`title`, `text`, `author_id`, `genre_id`) values('title2', 'text2', 3, 3);