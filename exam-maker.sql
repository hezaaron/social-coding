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
	`id` INTEGER NOT NULL AUTO_INCREMENT,
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
VALUES ('Sean', 'Jill', 'Sean Jill', 'sjill', '$2a$10$zI237fusXLAZ9l3J6006xuGg3lKtgg2ymkYVe4GF8tPE5VOcu3FiG', 'sjill@mail.com');


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
    `user_id` INTEGER NOT NULL,
    `user_profile_id` INTEGER NOT NULL,
    PRIMARY KEY (`user_id`, `user_profile_id`),
    CONSTRAINT FK_user FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT FK_user_profile FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
)ENGINE=InnoDB;

--
--Dumping data for table `user_user_profile`
--

INSERT INTO `user_user_profile` (user_id, user_profile_id)
	SELECT u.id, p.id FROM `user` u, `user_profile` p
	where u.user_name='sjill' and p.type='USER';

  
--
--Table structure for table `persistent_logins` to store rememberme flag
--

DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
    `user_name` VARCHAR(64) NOT NULL,
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
	`id` INTEGER NOT NULL AUTO_INCREMENT,
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
VALUES ('Java Entry Level', 'This exam is designed to test candidate applying for the Java entry level position. It consists of 10 questions to be answered in 20 minutes. Please login to take the exam.', 70, 100, 30),
	('Java Intermediate Level', 'This exam is designed to test candidate applying for the Java intermediate level position. It consists of 10 questions to be answered in 20 minutes. Please login to take the exam.', 70, 100, 30);


--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`exam_id` INTEGER NOT NULL,
	`title` VARCHAR(40) NOT NULL,
	`problem_description` BLOB NOT NULL,
	`multi_answer` BIT NOT NULL DEFAULT 0,
	`answer` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`, `exam_id`),
	CONSTRAINT FK_question FOREIGN KEY (`exam_id`) REFERENCES `test_exam` (`id`)
) ENGINE=InnoDB;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`exam_id`, `title`, `problem_description`, `multi_answer`, `answer`)
VALUES (1, 'Java Language', 'Java is platform independent, what does it mean?', 0, 'A java program can run on any operating system'),
		(1, 'Development Environment', 'What is JVM', 0, 'Java Virtual Machine responsible for converting byte code into machine readable code'),
		(1, 'Development Environment', 'What is JDK', 0, 'Java Development Kit provides the tools, executables and binaries to compile, debug and, execute a java program'),
		(1, 'Development Environment', 'What is the difference between JVM and JDK', 0, 'JVM is the part of JDK (Java Development Kit) that executes java programs' ),
		(1, 'Development Environment', 'What is JRE', 0, 'Java Runtime Environment is the implementation of JVM'),
		(1, 'Classes and Objects', 'Which class is the super class of all classes', 0, 'java.lang.Object is the root class/superclass of all java classes'),
		(1, 'Classes and Objects', 'What is Composition', 0, 'Composition is a class having references to objects of other classes as memebers'),
		(1, 'Classes and Objects', 'What is Enimeration', 0, 'Enumeration defines a set of contants represented as unique identifiers'),
		(1, 'Object Oriented Programming', 'What is inheritance', 0, 'Inheritance is a form of software reuse in which a new class is created by absorbing an existing class members'),
		(1, 'Object Oriented Programming', 'What is Polymorphism', 0, 'Polymorphism is enables you to write programs that process objects that share the same superclass');
 

--
-- Definition of table `question_choice`
--

DROP TABLE IF EXISTS `question_choice`;
CREATE TABLE `question_choice` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`question_id` INTEGER NOT NULL,
	`exam_id` INTEGER NOT NULL,
	`choice` VARCHAR(255) NOT NULL,
	`correct_choice` BIT NOT NULL DEFAULT 0,
	PRIMARY KEY(`id`),
	CONSTRAINT FK_question_choice FOREIGN KEY(`question_id`) REFERENCES `question`(`id`),
	CONSTRAINT FK_question_choice2 FOREIGN KEY(`exam_id`) REFERENCES `test_exam`(`id`)
) ENGINE=InnoDB;


--
-- Dumping data for table `question_choice`
--

INSERT INTO `question_choice` (`question_id`, `exam_id`, `choice`, `correct_choice`)
VALUES (1, 1, 'A java program can run on any operating system', 1),
        (1, 1, 'A java program can be referenced by other program', 0),
        (1, 1, 'A java program can run on many computer', 0),
        (2, 1, 'Java Virtual Machine translates java source code into bytecodes', 0),
        (2, 1, 'Java Virtual Machine responsible for converting byte code into machine readable code', 1),
        (2, 1, 'Java Virtual Machine compiles java source code', 0),
        (3, 1, 'Java Development Kit provides the tools, executables and binaries to compile, debug and, execute a java program', 1),
        (3, 1, 'Java Development Kit interpretes java byte code', 0),
        (3, 1, 'Java Development Kit loads class file from disc to a computer or network', 0),
        (4, 1, 'JDK is the part of JVM that compiles a java program', 0),
        (4, 1, 'JDK is invoked by java command to execute java application while JVM is used for development', 0),
        (4, 1, 'JVM is the part of JDK (Java Development Kit) that executes java programs', 1),
        (5, 1, 'Java Runtime Environment is the implementation of JVM', 1),
        (5, 1, 'Java Runtime Environment is the bytecode verifier for java classes', 0),
        (5, 1, 'Java Runtime Environment complies and runs java application', 0),
        (6, 1, 'java.lang.Object is the root class/superclass of all java classes', 1),
        (6, 1, 'java.lang.string is the root class/superclass of all java classes', 0),
        (6, 1, 'java.util.Scanner is the root class/superclass of all java classes', 0),
        (7, 1, 'Composition is a class that absorbs members of other classes', 0),
        (7, 1, 'Composition is a class having references to objects of other classes as memebers', 1),
        (7, 1, 'Composition is when a class extends other classes to use their memebers', 0),
        (8, 1, 'Enumeration defines a set of setter and getter methods', 0),
        (8, 1, 'Enumeration is a group of variables containing value that all have the same types', 0),
        (8, 1, 'Enumeration defines a set of contants represented as unique identifiers', 1),
		(9, 1, 'Inheritance is a form of software reuse in which a new class is created by absorbing an existing class members', 1),
		(9, 1, 'Inheritance is a blueprint for a software object', 0),
		(9, 1, 'Inheritance is a collection of methods with no implementation', 0),
		(10, 1, 'Polymorphism is a namespace that organises classes and interfaces', 0),
		(10, 1, 'Polymorphism enables you to write programs that process objects that share the same superclass', 1),
		(10, 1, 'Polymorphism is a technique for hiding internal data from the outside world', 0);
 

--
-- Definition of table `grade_result`
--

DROP TABLE IF EXISTS `grade_result`;
CREATE TABLE `grade_result` (
	`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`user_id` INTEGER NOT NULL,
	`exam_id` INTEGER NOT NULL,
	`start` DATETIME NOT NULL,
	`finish` DATETIME,
	`question_count` INTEGER,
	`correct_answers` INTEGER,
	`grade` INTEGER,
	CONSTRAINT FK_grade_result FOREIGN KEY(`user_id`) REFERENCES `user`(`id`),
	CONSTRAINT FK_grade_result2 FOREIGN KEY(`exam_id`) REFERENCES `test_exam`(`id`)
) ENGINE=InnoDB
