-- test.todo definition

CREATE TABLE test.todo (
	todo_id INT auto_increment NOT NULL,
	todo_text varchar(100) NOT NULL,
	complete_yn varchar(2) NOT NULL,
	reg_date DATETIME NOT NULL,
	mod_date DATETIME NOT NULL,
	CONSTRAINT todo_pk PRIMARY KEY (todo_id)
);