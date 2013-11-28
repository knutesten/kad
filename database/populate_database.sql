-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
USE kad;
INSERT INTO users
  (users_username, users_email, user_password, user_salt, user_fullname, user_country, user_countryCode)
VALUES
('admin','dummy@dummymail.com','f8b7f4089381a43b15089059c7780841ebd1986497eb44a2c968d9c06d973213','','Hestemannen',
 'Norway','no_NO');

-- -----------------------------------------------------
-- Populate userGroups table
-- -----------------------------------------------------

INSERT INTO userGroups
  (userGroups_userGroup)
VALUES
('admin'),
('moderator'),
('user');
