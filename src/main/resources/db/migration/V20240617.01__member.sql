-- test.member definition

CREATE TABLE test.member (
	member_id INT auto_increment NOT NULL COMMENT 'id',
	member_name varchar(20) NOT NULL COMMENT '이름',
	CONSTRAINT User_PK PRIMARY KEY (member_id)
);