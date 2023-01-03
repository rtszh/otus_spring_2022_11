insert into author (name)
values ('pushkin');
insert into author (name)
values ('gogol');
insert into author (name)
values ('heerdal');
insert into author (name)
values ('gutnik');
insert into author (name)
values ('perishkin');

insert into genre (name)
values ('action');
insert into genre (name)
values ('rpg');
insert into genre (name)
values ('other');

insert into book (title, author_id, genre_id)
values ('daughter', 1, 1);
insert into book (title, author_id, genre_id)
values ('souls', 2, 2);
insert into book (title, author_id, genre_id)
values ('aku-aku', 3, 1);
insert into book (title, author_id, genre_id)
values ('aku-aku', 3, 2);
insert into book (title, author_id, genre_id)
values ('physics', 4, 3);
insert into book (title, author_id, genre_id)
values ('physics', 5, 3);