INSERT INTO author (id, first_name, last_name) VALUES (1, 'Alex', 'Antonov');
INSERT INTO publisher (id, name) VALUES (1, 'Packt');
INSERT INTO book (id, isbn, title, author_id, publisher_id) VALUES (1, '978-1-78528-415-1', 'Spring Boot Recipes 2.0', 1, 1);
INSERT INTO reviewer (id, first_name, last_name) VALUES (1, 'Preston', 'Sheldon');
INSERT INTO book_reviewers (book_id, reviewers_id) VALUES (1,1);
