-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
USE kad;
INSERT INTO users
  (user_username, user_email, user_password, user_salt, user_fullname, user_country, user_locale)
VALUES
('admin','dummy@dummymail.com','f8b7f4089381a43b15089059c7780841ebd1986497eb44a2c968d9c06d973213','','Hestemannen',
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
