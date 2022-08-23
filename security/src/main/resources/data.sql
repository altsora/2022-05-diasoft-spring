INSERT INTO roles(name)
VALUES ('ROLE_USER');

INSERT INTO users(username, password)
VALUES ('alex', 'pass1'),
       ('bob', 'pass2');

INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);