-- Create the database
CREATE DATABASE IF NOT EXISTS `102203984`;
USE `102203984`;

-- Create Film table
CREATE TABLE Film (
                      Film_ID INT PRIMARY KEY AUTO_INCREMENT,
                      Titel VARCHAR(255) NOT NULL,
                      Genre VARCHAR(100),
                      Dauer DOUBLE NOT NULL
);

-- Create Saal (Theater Room) table
CREATE TABLE Saal (
                      Saal_ID INT PRIMARY KEY AUTO_INCREMENT,
                      max_Parkettplaetze INT NOT NULL,
                      max_Logenplaetze INT NOT NULL,
                      Ausstattung VARCHAR(255)
);

-- Create Gast (Guest) table
CREATE TABLE Gast (
                      Gast_ID INT PRIMARY KEY AUTO_INCREMENT,
                      Name VARCHAR(255) NOT NULL,
                      Email VARCHAR(255)
);

-- Create Vorstellung (Screening) table
CREATE TABLE Vorstellung (
                             Vorstellungs_ID INT PRIMARY KEY AUTO_INCREMENT,
                             Film_ID INT NOT NULL,
                             Vorfuehrungszeit DATETIME NOT NULL,
                             Saal_ID INT NOT NULL,
                             FOREIGN KEY (Film_ID) REFERENCES Film(Film_ID),
                             FOREIGN KEY (Saal_ID) REFERENCES Saal(Saal_ID)
);

-- Create Ticket table
CREATE TABLE Ticket (
                        Ticket_ID INT PRIMARY KEY AUTO_INCREMENT,
                        Vorstellungs_ID INT NOT NULL,
                        Gast_ID INT NOT NULL,
                        Platz_Typ BOOLEAN NOT NULL, -- FALSE = Parkett, TRUE = Loge
                        FOREIGN KEY (Vorstellungs_ID) REFERENCES Vorstellung(Vorstellungs_ID),
                        FOREIGN KEY (Gast_ID) REFERENCES Gast(Gast_ID)
);

-- Insert sample data

-- Films
INSERT INTO Film (Titel, Genre, Dauer) VALUES
                                           ('The Shawshank Redemption', 'Drama', 142),
                                           ('The Godfather', 'Crime', 175),
                                           ('Inception', 'Sci-Fi', 148),
                                           ('Interstellar', 'Sci-Fi', 169),
                                           ('The Dark Knight', 'Action', 152),
                                           ('Pulp Fiction', 'Crime', 154),
                                           ('Forrest Gump', 'Drama', 142),
                                           ('The Matrix', 'Sci-Fi', 136);

-- Theater Rooms
INSERT INTO Saal (max_Parkettplaetze, max_Logenplaetze, Ausstattung) VALUES
                                                                       (150, 50, 'Dolby Atmos, 4K Projection'),
                                                                       (120, 30, 'Standard Sound, 2K Projection'),
                                                                       (200, 60, 'IMAX, 3D Capability'),
                                                                       (80, 20, 'VIP Room, Premium Sound');

-- Guests
INSERT INTO Gast (Name, Email) VALUES
                                   ('Maria Schmidt', 'maria.schmidt@example.com'),
                                   ('Thomas Mueller', 'thomas.mueller@example.com'),
                                   ('Sophie Weber', 'sophie.weber@example.com'),
                                   ('Michael Becker', 'michael.becker@example.com'),
                                   ('Laura Fischer', 'laura.fischer@example.com'),
                                   ('Alexander König', 'alexander.koenig@example.com'),
                                   ('Julia Wagner', 'julia.wagner@example.com'),
                                   ('Daniel Schneider', 'daniel.schneider@example.com'),
                                   ('Sarah Hoffmann', 'sarah.hoffmann@example.com'),
                                   ('Markus Meyer', 'markus.meyer@example.com');

-- Screenings
INSERT INTO Vorstellung (Film_ID, Vorfuehrungszeit, Saal_ID) VALUES
                                                                (1, '2025-05-05 18:00:00', 1),
                                                                (2, '2025-05-05 20:30:00', 1),
                                                                (3, '2025-05-05 19:00:00', 2),
                                                                (4, '2025-05-05 21:30:00', 2),
                                                                (5, '2025-05-05 17:30:00', 3),
                                                                (6, '2025-05-05 20:00:00', 3),
                                                                (7, '2025-05-05 18:30:00', 4),
                                                                (8, '2025-05-05 21:00:00', 4),
                                                                (1, '2025-05-06 18:00:00', 2),
                                                                (2, '2025-05-06 19:30:00', 3);

-- Tickets
INSERT INTO Ticket (Vorstellungs_ID, Gast_ID, Platz_Typ) VALUES
                                                             (1, 1, FALSE), -- Parkett
                                                             (1, 2, TRUE),  -- Loge
                                                             (1, 3, FALSE),
                                                             (2, 4, TRUE),
                                                             (2, 5, TRUE),
                                                             (3, 6, FALSE),
                                                             (3, 7, FALSE),
                                                             (4, 8, TRUE),
                                                             (4, 9, FALSE),
                                                             (5, 10, TRUE),
                                                             (5, 1, FALSE),
                                                             (6, 2, FALSE),
                                                             (6, 3, TRUE),
                                                             (7, 4, FALSE),
                                                             (7, 5, FALSE),
                                                             (8, 6, TRUE),
                                                             (9, 7, FALSE),
                                                             (10, 8, TRUE);

-- Create view to see current movies
CREATE VIEW CurrentMovies AS
SELECT f.Film_ID, f.Titel, f.Genre, f.Dauer, v.Vorstellungs_ID, v.Vorfuehrungszeit, v.Saal_ID
FROM Film f
         JOIN Vorstellung v ON f.Film_ID = v.Film_ID
WHERE v.Vorfuehrungszeit > NOW()
ORDER BY v.Vorfuehrungszeit;

-- Create function to calculate free seats for a screening
DELIMITER //
CREATE FUNCTION FreeSeats(screening_id INT)
    RETURNS VARCHAR(100)
    DETERMINISTIC
BEGIN
    DECLARE total_parkett INT;
    DECLARE total_loge INT;
    DECLARE used_parkett INT;
    DECLARE used_loge INT;
    DECLARE free_parkett INT;
    DECLARE free_loge INT;
    DECLARE result VARCHAR(100);

    -- Get total seats from the theater room
    SELECT s.max_Parkettplaetze, s.max_Logenplaetze
    INTO total_parkett, total_loge
    FROM Vorstellung v
             JOIN Saal s ON v.Saal_ID = s.Saal_ID
    WHERE v.Vorstellungs_ID = screening_id;

    -- Get used seats
    SELECT
        SUM(CASE WHEN Platz_Typ = FALSE THEN 1 ELSE 0 END),
        SUM(CASE WHEN Platz_Typ = TRUE THEN 1 ELSE 0 END)
    INTO used_parkett, used_loge
    FROM Ticket
    WHERE Vorstellungs_ID = screening_id;

    -- Calculate free seats
    SET free_parkett = total_parkett - IFNULL(used_parkett, 0);
    SET free_loge = total_loge - IFNULL(used_loge, 0);

    SET result = CONCAT('Free Parkett: ', free_parkett, ', Free Loge: ', free_loge);

    RETURN result;
END //
DELIMITER ;

-- Create procedure to create tickets
DELIMITER //
CREATE PROCEDURE CreateTickets(
    IN p_vorstellungs_id INT,
    IN p_gast_id INT,
    IN p_parkett_count INT,
    IN p_loge_count INT
)
BEGIN
    DECLARE i INT DEFAULT 0;

    -- Create Parkett tickets
    WHILE i < p_parkett_count DO
            INSERT INTO Ticket (Vorstellungs_ID, Gast_ID, Platz_Typ)
            VALUES (p_vorstellungs_id, p_gast_id, FALSE);
            SET i = i + 1;
        END WHILE;

    -- Reset counter
    SET i = 0;

    -- Create Loge tickets
    WHILE i < p_loge_count DO
            INSERT INTO Ticket (Vorstellungs_ID, Gast_ID, Platz_Typ)
            VALUES (p_vorstellungs_id, p_gast_id, TRUE);
            SET i = i + 1;
        END WHILE;

    -- Return confirmation
    SELECT CONCAT(p_parkett_count + p_loge_count, ' tickets created successfully') AS Result;
END //
DELIMITER ;