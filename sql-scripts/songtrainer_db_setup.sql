DROP DATABASE  IF EXISTS `songtrainer`;

CREATE DATABASE  IF NOT EXISTS `songtrainer`;
USE `songtrainer`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` char(80) NOT NULL,
    `first_name` varchar(50) NOT NULL,
    `last_name` varchar(50) NOT NULL,
    `email` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
-- The passwords are encrypted using bcrypt
-- Default passwords here are: fun123
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES
    ('john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','John','Doe','john@mail.com'),
    ('mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Mary','Public','mary@mail.com');


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `role` (name)
VALUES
    ('ROLE_USER'),('ROLE_LECTOR');

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
   `user_id` int(11) NOT NULL,
   `role_id` int(11) NOT NULL,

   PRIMARY KEY (`user_id`,`role_id`),

   KEY `FK_ROLE_idx` (`role_id`),

   CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`)
       REFERENCES `user` (`id`)
       ON DELETE NO ACTION ON UPDATE NO ACTION,

   CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`)
       REFERENCES `role` (`id`)
       ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2);

--
-- Table structure for table `song`
--
DROP TABLE IF EXISTS `song`;

CREATE TABLE IF NOT EXISTS `song` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NOT NULL,
    `artist` VARCHAR(45) NOT NULL,
    `instrument` ENUM('guitar', 'bass', 'drums', 'piano') NOT NULL,
    `tuning` ENUM('E standard', '1/2 step down', 'D standard', 'drop D', 'drop C') NULL,
    `length` INT NULL DEFAULT 0,
    `times_played` INT NULL DEFAULT 0,
    `last_played` DATETIME NULL,
    `user_id` INT(11) NOT NULL,

    PRIMARY KEY (`id`, `user_id`),

    INDEX `fk_song_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_song_user1` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO `song` (title,artist,instrument,tuning,user_id)
VALUES
    ('Whatsername', 'Green Day', 'guitar', 'drop D', 1),
    ('Holiday', 'Green Day', 'bass', 'E standard', 1),
    ('Message in a bottle', 'The Police', 'guitar', 'E standard', 2);

--
-- Table structure for table `playlist`
--
DROP TABLE IF EXISTS `playlist`;

CREATE TABLE IF NOT EXISTS `playlist` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `instrument` ENUM('guitar', 'bass', 'drums', 'piano') NULL,
    `user_id` INT(11) NOT NULL,

    PRIMARY KEY (`id`, `user_id`),

    INDEX `fk_playlist_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_playlist_user1`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO `playlist` (name,instrument,user_id)
VALUES
    ('New wave', 'guitar', 2);
--
-- Table structure for table `playlist_has_song`
--
DROP TABLE IF EXISTS `playlist_has_song`;

CREATE TABLE IF NOT EXISTS `playlist_has_song` (
    `playlist_id` INT NOT NULL,
    `playlist_user_id` INT(11) NOT NULL,
    `song_id` INT NOT NULL,
    `song_user_id` INT(11) NOT NULL,

    PRIMARY KEY (`playlist_id`, `playlist_user_id`, `song_id`, `song_user_id`),

    INDEX `fk_playlist_has_song_song1_idx` (`song_id` ASC, `song_user_id` ASC) VISIBLE,
    INDEX `fk_playlist_has_song_playlist1_idx` (`playlist_id` ASC, `playlist_user_id` ASC) VISIBLE,
    CONSTRAINT `fk_playlist_has_song_playlist1` FOREIGN KEY (`playlist_id` , `playlist_user_id`)
        REFERENCES `playlist` (`id` , `user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `fk_playlist_has_song_song1` FOREIGN KEY (`song_id` , `song_user_id`)
        REFERENCES `song` (`id` , `user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO playlist_has_song (playlist_id, playlist_user_id, song_id, song_user_id)
VALUES
    (1, 2, 3, 2);

SET FOREIGN_KEY_CHECKS = 1;