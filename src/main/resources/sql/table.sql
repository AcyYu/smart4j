CREATE TABLE customer(
id bigint(20) NOT NULL auto_increment,
name VARCHAR (255) DEFAULT NULL ,
contact VARCHAR (255) DEFAULT NULL ,
telephone VARCHAR (255) DEFAULT NULL ,
email VARCHAR (255) DEFAULT NULL ,
remark text,
PRIMARY KEY (id)
);