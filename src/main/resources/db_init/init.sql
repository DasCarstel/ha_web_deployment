CREATE DATABASE IF NOT EXISTS `12345678_kino`;
USE `12345678_kino`;

CREATE TABLE kunde (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       telefonnummer VARCHAR(20) NOT NULL
);