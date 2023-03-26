-- liquibase formatted sql
-- changeset liquibase:1

INSERT INTO role VALUES ('ADMIN');
INSERT INTO role VALUES ('CREATOR');

INSERT INTO Users VALUES ('5bb0a21e-72bf-4b19-845e-6c99dfd6524a',0 , 'Admin', 'Adminowy', 'admin', '$2a$10$lvzEYQDMYuE0hBwDuvwC9ucGg/milF1gAQE7eF2/cnbRwqY34KcUi','ADMIN');
INSERT INTO Users VALUES ('4440a21e-72bf-4b19-845e-6c99dfd65222',0 , 'Creator', 'Zdostepem', 'creator', '$2a$10$lvzEYQDMYuE0hBwDuvwC9ucGg/milF1gAQE7eF2/cnbRwqY34KcUi','CREATOR');
INSERT INTO Users VALUES ('6640a21e-72bf-4b19-845e-6c99dfd65222',0 , 'Creator', 'Bezdostepu', 'creatorWithNoAccess', '$2a$10$lvzEYQDMYuE0hBwDuvwC9ucGg/milF1gAQE7eF2/cnbRwqY34KcUi','CREATOR');
