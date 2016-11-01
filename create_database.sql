
# CREATE DATABASE IF NOT EXISTS `maze_db`;
# USE `maze_db`;

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `session`;
SET FOREIGN_KEY_CHECKS=1;


CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`login` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`max_score` int(8) DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE (login)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `session` (
	`id` int (11) NOT NULL AUTO_INCREMENT,
	`session_id` varchar(250) NOT NULL,
	`user_id` int (11) NOT NULL,
	`creation_time` DATETIME NOT NULL,
	`last_accessed_time` DATETIME NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (user_id) REFERENCES user(id)
	ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
