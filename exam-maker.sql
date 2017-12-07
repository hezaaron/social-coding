-- MySQL dump   Distrib 5.7.20
--
-- Host: localhost    Database: exam_maker
-- ---------------------------------------
-- Server version: 5.7.20 - community




--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	`first_name` VARCHAR(40) NOT NULL,
	`last_name` VARCHAR(40) NOT NULL,
	`full_name` VARCHAR(50) NOT NULL,
	`user_name` VARCHAR(45) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`email` VARCHAR(40) NOT NULL,
	PRIMARY KEY(`id`),
	UNIQUE(`user_name`)
) ENGINE=InnoDB;


--
--Dumping data for table `user`
--

INSERT INTO `user` (`first_name`, `last_name`, `full_name`, `user_name`, `password`, `email`)
VALUES ();


--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`type`)
) ENGINE=InnoDB;


--
--Dumping data for table `user_profile`
--

INSERT INTO `user_profile` (`type`)
VALUES ('USER'), ('ADMIN'), ('DBA');


--
-- Table structure for join table `user_user_profile` for many-to-many relationship
--

DROP TABLE IF EXISTS `user_user_profile`;
CREATE TABLE `user_user_profile` (
    `user_id` BIGINT NOT NULL,
    `user_profile_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `user_profile_id`),
    CONSTRAINT FK_user FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile (id)
)ENGINE=InnoDB;


--
--Table structure for table `persistent_logins` to store rememberme flag
--

CREATE TABLE `persistent_logins` (
    `username` VARCHAR(64) NOT NULL,
    `series` VARCHAR(64) NOT NULL,
    `token` VARCHAR(64) NOT NULL,
    `last_used` TIMESTAMP NOT NULL,
    PRIMARY KEY (`series`)
)ENGINE=InnoDB;


--
-- Table structure for table `test_exam`
--

DROP TABLE IF EXISTS `test_exam`;
CREATE TABLE `test_exam` (
	`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`description` MEDIUMTEXT NOT NULL,
	`pass_score` INTEGER NOT NULL,
	`total_score` INTEGER NOT NULL,
	`exam_duration` INTEGER NOT NULL,
	PRIMARY KEY(`id`),
	UNIQUE(`name`)
) ENGINE=InnoDB;


--
--Dumping data for table `test_exam`
--

INSERT INTO `test_exam` (`name`, `description`, `pass_score`, `total_score`, `exam_duration`)
VALUES ('Java Entry Level', 'This exam is designed to test candidate applying for the Java entry level position. It consists of 10 questions to be answered in 30 minutes', 60, 100, 30),
	('Java Intermediate Level', 'This exam is designed to test candidate applying for the Java intermediate level position. It consists of 10 questions to be answered in 30 minutes', 60, 100, 30);


--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
	`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(10) NOT NULL,
	`problem_description` BLOB NOT NULL,
	`multi_answer` INTEGER NOT NULL,
	`answer` VARCHAR(40),
	PRIMARY KEY(`id`),
	UNIQUE(`title`)
) ENGINE=InnoDB

--
-- Dumping data for table `question`
--

INSERT INTO `questions` (`id`, `title`, `problem_description`, `multi_answer`, `answer`)
VALUES (1, 'qtn1','What is the importance of main method in java', 1, )


--
-- Table structure for table `answer_choice`
--

DROP TABLE IF EXISTS `question_answer`;
CREATE TABLE `question_answer` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `answer_text` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
  );

 

--
-- Table structure for table `question_choices`
--

DROP TABLE IF EXISTS `question_choices`;
CREATE TABLE `question_choices` (
	`sequence` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	`question_id` INTEGER UNSIGNED NOT NULL,
	`choice_text` VARCHAR(100) NOT NULL DEFAULT '',
	`correct_choice` VARCHAR(40),
	PRIMARY KEY(`sequence`, `question_id`),
	CONSTRAINT FK_question_choice FOREIGN KEY(`question_id`) REFERENCES question (`id`),
) ENGINE=InnoDB



--
-- Definition of table `grade_result`
--

DROP TABLE IF EXISTS `grade_result`;
CREATE TABLE `grade_result` (
	`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INTEGER UNSIGNED NOT NULL,
	`exam_id` INTEGER UNSIGNED NOT NULL,
	`grade` INTEGER NOT NULL,
	`exam_date` DATETIME NOT NULL,
	PRIMARY KEY(`user_id`, `exam_id`, `exam_date`),
	CONSTRAINT FK_grade_result FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
	CONSTRAINT FK_grade_result2 FOREIGN KEY(`exam_id`) REFERENCES `test_exam`(`id`)
) ENGINE=InnoDB
