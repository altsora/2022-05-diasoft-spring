INSERT INTO author(id, first_name, last_name) VALUES (1, 'author_1_first_name', 'author_1_last_name');
INSERT INTO author(id, first_name, last_name) VALUES (2, 'author_2_first_name', 'author_2_last_name');

INSERT INTO genre(id, name) VALUES(1, 'genre_1');
INSERT INTO genre(id, name) VALUES(2, 'genre_2');

INSERT INTO book(id, title, author_id, genre_id) VALUES (1, 'book_1', 1, 1);
INSERT INTO book(id, title, author_id, genre_id) VALUES (2, 'book_2', 1, 2);
INSERT INTO book(id, title, author_id, genre_id) VALUES (3, 'book_3', 2, 2);

INSERT INTO book_comment(id, book_id, text) VALUES (1, 1, 'comment_1_1');
INSERT INTO book_comment(id, book_id, text) VALUES (2, 1, 'comment_1_2');
INSERT INTO book_comment(id, book_id, text) VALUES (3, 1, 'comment_1_3');
INSERT INTO book_comment(id, book_id, text) VALUES (4, 2, 'comment_2_1');