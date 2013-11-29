-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
USE kad;
INSERT INTO users
  (users_username, users_email, users_password, users_salt, users_fullname, users_country, users_locale)
VALUES
('admin','dummy@dummymail.com','f8b7f4089381a43b15089059c7780841ebd1986497eb44a2c968d9c06d973213','','Hestemannen',
 'Norway','no_NO');

-- -----------------------------------------------------
-- Populate userGroups table
-- -----------------------------------------------------

INSERT INTO userGroups
  (userGroups_userGroup)
VALUES
('Administrator'),
('Moderator'),
('User');

-- -----------------------------------------------------
-- Create usersInUserGroup table
-- -----------------------------------------------------

INSERT INTO usersInUserGroup
(usersInUserGroup_username, usersInUserGroup_userGroup)
VALUES
('admin','Administrator');
