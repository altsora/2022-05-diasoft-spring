DELETE FROM users;
INSERT INTO users(id, username, password, role)
VALUES (-1, 'user_guest', 'passtest', 'GUEST'),
       (-2, 'user_user', 'passtest', 'USER'),
       (-3, 'user_admin', 'passtest', 'ADMIN');

DELETE FROM books;
INSERT INTO books(id, title)
VALUES (1, 'Test book');