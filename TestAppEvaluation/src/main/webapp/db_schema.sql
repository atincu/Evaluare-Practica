create database if not exists GraduatesApp;
use GraduatesApp;


create table if not exists tvseries(
	id INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
	title VARCHAR(100) DEFAULT NULL,
	year INT,
	poster VARCHAR(100) DEFAULT NULL,
	genre VARCHAR(100) DEFAULT NULL,
	plot BLOB, 
	overview BLOB,
	actors VARCHAR(100) DEFAULT NULL
);

