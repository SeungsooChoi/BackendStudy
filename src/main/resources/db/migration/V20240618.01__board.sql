-- test.board definition

CREATE TABLE test.board (
	board_id INT auto_increment NOT NULL,
	board_title varchar(20) NOT NULL,
	board_content TEXT NULL,
	board_nickname varchar(10) NOT NULL,
	CONSTRAINT board_pk PRIMARY KEY (board_id)
);