CREATE TABLE `member_refresh_token` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `refresh_token` varchar(255) NOT NULL,
  `expiry_date` datetime(6) NOT NULL,
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `refresh_token` (`refresh_token`)
)