-- test.comment definition

CREATE TABLE test.comment (
	comment_id INT auto_increment NOT NULL,
	board_id INT NOT NULL,
	comment_content varchar(100) NOT NULL,
	comment_nickname varchar(20) NOT NULL,
	CONSTRAINT comment_pk PRIMARY KEY (comment_id),
	CONSTRAINT comment_board_FK FOREIGN KEY (board_id) REFERENCES test.board(board_id)
);