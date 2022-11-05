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
    `profile_picture_path` varchar(512),
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
    ('mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Mary','Public','mary@mail.com'),
    ('adam','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Adam','Addams','adam@mail.com'),
    ('susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Susan','Public','susan@mail.com');

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
    (2, 2),
    (3, 1),
    (4, 1);

--
-- Table structure for table `song`
--
DROP TABLE IF EXISTS `song`;

CREATE TABLE IF NOT EXISTS `song` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NOT NULL,
    `artist` VARCHAR(45) NOT NULL,
    `instrument` VARCHAR(45) NOT NULL,
    `tuning` VARCHAR(45) NULL,
    `length` INT NULL DEFAULT 0,
    `times_played` INT NULL DEFAULT 0,
    `last_played` DATETIME NULL,
    `user_id` INT(11) NOT NULL,
    `file_name` VARCHAR(512) DEFAULT NULL,
    `is_visible` BOOLEAN DEFAULT false,

    PRIMARY KEY (`id`),

    INDEX `fk_song_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_song_user1` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO `song` (title,artist,instrument,tuning,user_id, length, times_played, last_played, is_visible)
VALUES
    ('Whatsername', 'Green Day', 'GUITAR', 'DROP_D', 1, 123, 4, '2022-10-03 05:15:00', true), -- 1
    ('Holiday', 'Green Day', 'BASS', 'E_STANDARD', 1, 150, 5, '2021-12-31 03:15:00', false), -- 2
    ('Message in a bottle', 'The Police', 'GUITAR', 'E_STANDARD', 2, 180, 12, '2022-09-20 07:15:00', true), -- 3
    ('Holiday', 'Green Day', 'DRUMS', 'NONE', 1, 130, 42, '2022-05-13 08:15:00', false), -- 4
    ('Heroes', 'David Bowie', 'GUITAR', 'E_STANDARD', 1,  200, 3, '2022-11-20 04:15:00', true), -- 5
    ('Walking on the moon', 'The Police', 'GUITAR', 'E_STANDARD', 2, 180, 1, '2021-06-25 06:15:00', true); -- 6

INSERT INTO `song` (title,artist,instrument,tuning,user_id, length, is_visible)
VALUES
    ('Walking on the moon', 'The Police', 'BASS', 'E_STANDARD', 2, 180, true), -- 7
    ('Message in a bottle', 'The Police', 'DRUMS', 'NONE', 2, 180, false), -- 8
    ('Square Hammer', 'Ghost', 'GUITAR', 'D_STANDARD', 3, 190, true), -- 9
    ('Poison', 'Alice Cooper', 'GUITAR', 'E_STANDARD', 3, 180, true), -- 10
    ('Hey stoopid', 'Alice Cooper', 'BASS', 'E_STANDARD', 4, 180, true); -- 10


--
-- Table structure for table `playlist`
--
DROP TABLE IF EXISTS `playlist`;

CREATE TABLE IF NOT EXISTS `playlist` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `instrument` VARCHAR(45) NULL,
    `user_id` INT(11) NOT NULL,

    PRIMARY KEY (`id`),

    INDEX `fk_playlist_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_playlist_user1`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO `playlist` (name,instrument,user_id)
VALUES
    ('New wave', 'GUITAR', 2),
    ('My Green Day playlist', 'GUITAR', 1),
    ('My drum playlist', 'DRUMS', 1);
--
-- Table structure for table `songs_playlists`
--
DROP TABLE IF EXISTS `songs_playlists`;

CREATE TABLE IF NOT EXISTS `songs_playlists` (
    `song_id` INT(11) NOT NULL,
    `playlist_id` INT(11) NOT NULL,

    PRIMARY KEY (`song_id`, `playlist_id`),

    INDEX `fk_songs_playlists_song1_idx` (`song_id` ASC) VISIBLE,
    INDEX `fk_songs_playlists_playlist1_idx` (`playlist_id` ASC) VISIBLE,
    CONSTRAINT `fk_songs_playlists_playlist1` FOREIGN KEY (`playlist_id`)
        REFERENCES `playlist` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_songs_playlists_song1` FOREIGN KEY (`song_id`)
        REFERENCES `song` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET=latin1;

-- Insert test values
INSERT INTO songs_playlists (song_id, playlist_id)
VALUES
    (3, 1),
    (1, 2),
    (6, 1),
    (4,3);

SET FOREIGN_KEY_CHECKS = 1;

-- Friends table

CREATE TABLE user_has_friend
(
    `user_id` INT(11),
    `friend_id` INT(11),
    PRIMARY KEY (`user_id`, `friend_id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_friend` FOREIGN KEY (`friend_id`)
        REFERENCES `user` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Insert test values
INSERT INTO `user_has_friend` (user_id,friend_id)
VALUES
    (1, 2), -- John & Mary
    (2, 1), -- Mary & John
    (1, 3), -- John & Adam
    (3, 1)  -- Adam & John