-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
USE kad;
INSERT INTO users
  (user_username, user_email, user_password, user_salt, user_fullname, user_country, user_locale)
VALUES
('admin','dummy@dummymail.com','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08','','Hestemannen',
 'Norway','no_NO');

-- -----------------------------------------------------
-- Populate userGroups table
-- -----------------------------------------------------

INSERT INTO userGroups
  (userGroup_userGroup)
VALUES
('Administrator'),
('Moderator'),
('User');

-- -----------------------------------------------------
-- Create usersInUserGroup table
-- -----------------------------------------------------

INSERT INTO usersInUserGroup
(usersInUserGroup_userId, usersInUserGroup_userGroup)
VALUES
((select user_id from users where user_username='admin'),'Administrator');
