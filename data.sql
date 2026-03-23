--users
INSERT INTO "user"(email, name, surname, role, password) VALUES ('prova@email.com',
                                                                 'Mario',
                                                                 'Rossi',
                                                                 'librarian'::role,
                                                                 '$2y$10$w3w4T3M1ddIhahKNE6nTG.GwI1l6aWXLIBGY.2xgM1533pltSlO.K'); -- hash di "bibliotecario"
INSERT INTO "user"(email, name, surname, role, password) VALUES ('email@email.com',
                                                                 'Luca',
                                                                 'Bianchi',
                                                                 'libraryUser'::role,
                                                                 '$2y$10$ZWvVs10j.qMdOH9H3Cg1S.ZREIdFwSwvf9Vd8pQnEWTFXt1I78v9O'); --hash di "libraryUser"
INSERT INTO "user"(email, name, surname, role, password) VALUES ('lampa.dario@email.com',
                                                                 'Dario',
                                                                 'Lampa',
                                                                 'libraryUser'::role,
                                                                 'hashedPassword');
INSERT INTO "user"(email, name, surname, role, password) VALUES ('admin@email.com',
                                                                 'Amministratore',
                                                                 'Accanito',
                                                                 'libraryAdministrator'::role,
                                                                 'hashedPassword');
INSERT INTO "user"(email, name, surname, role, password) VALUES ('neri.neri@email.com',
                                                                 'Neri',
                                                                 'Neri',
                                                                 'libraryUser'::role,
                                                                 '$2y$10$YDUcy27VAQK9qs5RnzvDcOL4yxllqN6htvPoQOjC0f9B4wEekmcju'); --hash di "NeriNeri"
INSERT INTO "user"(email, name, surname, role, password) VALUES ('gino.verdi@email.com',
                                                                 'Gino',
                                                                 'Verdi',
                                                                 'libraryUser'::role,
                                                             '$2y$10$Wn6bGp7zidi.v4OBUHCUvu1RlmhqDnfF5fH.mrTlK/Ei0WZ5rk3mi'); --HASH DI "gino"
--cards
INSERT INTO card(issuedate, "user") VALUES ('2026-03-17', 'email@email.com'); --valid card
INSERT INTO card("user") VALUES ('lampa.dario@email.com'); --requested card
INSERT INTO card("user", issuedate) VALUES ('gino.verdi@email.com', '2023-01-01'); --expired card
--rooms
INSERT INTO room(number, seats, is_study_room) VALUES (1, 50, false);
INSERT INTO room(number, seats, is_study_room) VALUES (2, 35, true);
INSERT INTO room(number, seats, is_study_room) VALUES (3, 20, false);
INSERT INTO room(number, seats, is_study_room) VALUES (4, 35, true);
--events
INSERT INTO event(name, description, date, duration, organizer, room, room_type)
VALUES ('presentazione libro aaa bbb',
        'descrizione',
        '2026-03-17 10:00:00',
        '1 hour',
        'prova@email.com',
        1, false);
--library
INSERT INTO library(name, budget) VALUES ('biblioteca libri belli', 125000);
--manage
INSERT INTO manage("user", library) VALUES ('admin@email.com', 'biblioteca libri belli');
--book
INSERT INTO book(code, isbn, title) VALUES ('A001', '1234567890123', 'titolo di un libro');
INSERT INTO book(code, isbn, title) VALUES ('A002', '1234567890124', 'titolo di un altro libro');
--reservation study room
INSERT INTO reservation_study_room("user", room, room_type)
VALUES ('lampa.dario@email.com', 2, true);
--loan
INSERT INTO loan(book, card, issueDate, expirationDate, granted, ended) VALUES ('A001', 1, '2026-03-17', '2026-04-17', true, false);