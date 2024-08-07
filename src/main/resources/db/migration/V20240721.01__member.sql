ALTER TABLE member
ADD COLUMN `member_email` varchar(100) NOT NULL COMMENT '유저 이메일 (로그인용)',
ADD COLUMN `member_password` varchar(100) NOT NULL COMMENT '유저 비밀번호 (암호화된)',
ADD COLUMN `member_authority` varchar(100) NOT NULL DEFAULT 'ROLE_MEMBER' COMMENT '유저 권한',
ADD COLUMN `last_login_date` datetime(6) DEFAULT NULL COMMENT '마지막 로그인 일시',
ADD COLUMN `reg_date` datetime(6) DEFAULT NULL COMMENT '가입일',
ADD COLUMN `mod_date` datetime(6) DEFAULT NULL COMMENT '수정일';