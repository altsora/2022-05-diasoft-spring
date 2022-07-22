DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    title     VARCHAR(100) NOT NULL,
    author_id INT          NOT NULL,
    genre_id  INT          NOT NULL,
    foreign key (author_id) references author (id),
    foreign key (genre_id) references genre (id)
);

DROP TABLE IF EXISTS book_comment;
CREATE TABLE book_comment
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT          NOT NULL,
    text    VARCHAR(500) NOT NULL,
    foreign key (book_id) references book (id) ON DELETE CASCADE
)