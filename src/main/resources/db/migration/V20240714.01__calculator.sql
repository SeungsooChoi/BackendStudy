-- test.calculator definition

CREATE TABLE test.calculator (
	calculator_id INT auto_increment NOT NULL,
	calculator_formula varchar(50) NOT NULL,
	calculator_answer INT NOT NULL,
	reg_date DATETIME NULL,
	CONSTRAINT calculator_pk PRIMARY KEY (calculator_id)
);