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
-- Default passwords here are: password
--

INSERT INTO `user` (username,password,first_name,last_name,email)
VALUES
    ('john','$2a$10$Ixr1HsJiMyURJK9d83alIOZplANpE1IPDKokqZrwbteBxhaBnJSc2','John','Doe','john@mail.com'),
    ('mary','$2a$10$Ixr1HsJiMyURJK9d83alIOZplANpE1IPDKokqZrwbteBxhaBnJSc2','Mary','Public','mary@mail.com');

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
    `assigned_by` INT(11) DEFAULT NULL,

    PRIMARY KEY (`id`),

    INDEX `fk_song_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_song_user1` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_song_assigned_by` FOREIGN KEY (`assigned_by`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `playlist`
--
DROP TABLE IF EXISTS `playlist`;

CREATE TABLE IF NOT EXISTS `playlist` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `instrument` VARCHAR(45) NULL,
    `user_id` INT(11) NOT NULL,
    `public` BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY (`id`),

    INDEX `fk_playlist_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_playlist_user1`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `rating`;

CREATE TABLE IF NOT EXISTS `rating` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `feelings` VARCHAR(150) NOT NULL,
    `accuracy` VARCHAR(45) NULL,
    `number_of_mistakes` VARCHAR(45) NULL,
    `comment` MEDIUMTEXT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `song_ratings`;

CREATE TABLE IF NOT EXISTS `song_ratings` (
    `rating_id` INT(11) NOT NULL,
    `song_id` INT(11) NOT NULL,
    PRIMARY KEY (`rating_id`, `song_id`),
    INDEX `fk_song_ratings_song_idx` (`song_id` ASC) VISIBLE,
    INDEX `fk_song_ratings_rating_idx` (`rating_id` ASC) VISIBLE,
    CONSTRAINT `fk_song_ratings_rating` FOREIGN KEY (`rating_id`)
    REFERENCES `rating` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_song_ratings_song` FOREIGN KEY (`song_id`)
    REFERENCES `song` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;


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

SET FOREIGN_KEY_CHECKS = 1;

-- Friends table
DROP TABLE IF EXISTS `user_follows`;

CREATE TABLE IF NOT EXISTS `user_follows` (
    `user_id` INT(11),
    `followed_id` INT(11),
    PRIMARY KEY (`user_id`, `followed_id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_followed` FOREIGN KEY (`followed_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

-- Insert test values
INSERT INTO `user_follows` (user_id,followed_id)
VALUES
    (1, 2), -- John & Mary
    (2, 1); -- Mary & John

-- -----------------------------------------------------
-- Table `user_has_students`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_has_students`;

CREATE TABLE IF NOT EXISTS `user_has_students` (
    `user_id` INT(11) NOT NULL,
    `student_id` INT(11) NOT NULL,
    PRIMARY KEY (`user_id`, `student_id`),
    CONSTRAINT `fk_user_students_user` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_students_student` FOREIGN KEY (`student_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

-- Insert test values
INSERT INTO `user_has_students` (user_id,student_id)
VALUES
    (2, 1); -- Mary & John


-- -----------------------------------------------------
-- Table `user_has_lectors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_has_lectors`;

CREATE TABLE IF NOT EXISTS `user_has_lectors` (
    `user_id` INT(11) NOT NULL,
    `lector_id` INT(11) NOT NULL,
    PRIMARY KEY (`user_id`, `lector_id`),
    CONSTRAINT `fk_user_lectors_user` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_lectors_lector` FOREIGN KEY (`lector_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

-- Insert test values
INSERT INTO `user_has_lectors` (user_id,lector_id)
VALUES
    (1, 2); -- Mary & John