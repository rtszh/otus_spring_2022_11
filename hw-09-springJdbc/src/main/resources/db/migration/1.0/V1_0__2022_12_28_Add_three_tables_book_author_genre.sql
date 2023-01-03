CREATE TABLE author
(
    id   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
--     id   BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE genre
(
    id   BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);


CREATE TABLE book
(
    id        BIGSERIAL NOT NULL PRIMARY KEY,
    title     VARCHAR(50),
    author_id BIGINT,
    genre_id  BIGINT,
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) REFERENCES genre (id)
);
