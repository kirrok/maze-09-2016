CREATE TABLE user (
	id int(11) NOT NULL AUTO_INCREMENT,
	login varchar(50) NOT NULL UNIQUE,
	password varchar(50) NOT NULL,
	max_score int(8) DEFAULT NULL,
	PRIMARY KEY (id)
);