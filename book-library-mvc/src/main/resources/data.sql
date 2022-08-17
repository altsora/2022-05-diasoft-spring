INSERT INTO author(id, first_name, last_name) VALUES (1, 'Domenic', 'Dickson');
INSERT INTO author(id, first_name, last_name) VALUES (2, 'Eileen', 'Corbett');
INSERT INTO author(id, first_name, last_name) VALUES (3, 'William', 'Robe');
INSERT INTO author(id, first_name, last_name) VALUES (4, 'Zara', 'Thomas');
INSERT INTO author(id, first_name, last_name) VALUES (5, 'Daron', 'Allcott');
INSERT INTO author(id, first_name, last_name) VALUES (6, 'Angelica', 'Collis');
INSERT INTO author(id, first_name, last_name) VALUES (7, 'Abdul', 'Potts');
INSERT INTO author(id, first_name, last_name) VALUES (8, 'Sienna', 'Murphy');
INSERT INTO author(id, first_name, last_name) VALUES (9, 'Ryan', 'Butler');
INSERT INTO author(id, first_name, last_name) VALUES (10, 'Estrella', 'Fleming');

INSERT INTO genre(id, name) VALUES(1,'History');
INSERT INTO genre(id, name) VALUES(2,'Drama');
INSERT INTO genre(id, name) VALUES(3,'Animation');
INSERT INTO genre(id, name) VALUES(4,'Musical');
INSERT INTO genre(id, name) VALUES(5,'Fantasy');
INSERT INTO genre(id, name) VALUES(6,'Romance');
INSERT INTO genre(id, name) VALUES(7,'Sci-Fi');
INSERT INTO genre(id, name) VALUES(8,'Biography');
INSERT INTO genre(id, name) VALUES(9,'Adventure');
INSERT INTO genre(id, name) VALUES(10,'Comedy');

INSERT INTO book(id, title, author_id, genre_id) VALUES(1,'Great Expectations',6,6);
INSERT INTO book(id, title, author_id, genre_id) VALUES(2,'Moby Dick',4,1);
INSERT INTO book(id, title, author_id, genre_id) VALUES(3,'The Metamorphosis',1,7);
INSERT INTO book(id, title, author_id, genre_id) VALUES(4,'Pride and Prejudice',6,2);
INSERT INTO book(id, title, author_id, genre_id) VALUES(5,'The Scarlet Letter',5,5);
INSERT INTO book(id, title, author_id, genre_id) VALUES(6,'Alice Adventures in Wonderland',3,6);
INSERT INTO book(id, title, author_id, genre_id) VALUES(7,'To Kill a Mockingbird',7,6);
INSERT INTO book(id, title, author_id, genre_id) VALUES(8,'Tristram Shandy',1,3);
INSERT INTO book(id, title, author_id, genre_id) VALUES(9,'The Divine Comedy',2,4);
INSERT INTO book(id, title, author_id, genre_id) VALUES(10,'Tess of the d`Urbervilles',3,6);

INSERT INTO book_comment(book_id, text) VALUES (1, 'Cool book!');
INSERT INTO book_comment(book_id, text) VALUES (1, 'Great');
INSERT INTO book_comment(book_id, text) VALUES (2, 'Very baaaad');