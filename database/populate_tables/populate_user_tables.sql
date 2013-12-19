USE kad;
-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
INSERT INTO users
  (user_username, user_email, user_hash, user_salt, user_fullname, user_country, user_locale)
VALUES
('admin','dummy@dummymail.com','ec29de432d53e2ddd0b574a4b5d6250667aefa259c1b14ba3a9d7289d642c01e',
'80224678a05b29556a67e06db63ff4561623e50e402ee2e3b8c5b9bf049ca23c','Hestemannen','GS','en_GB'),
('user','dummy@dummymail2.com','ec29de432d53e2ddd0b574a4b5d6250667aefa259c1b14ba3a9d7289d642c01e',
'80224678a05b29556a67e06db63ff4561623e50e402ee2e3b8c5b9bf049ca23c','Hestemannen','NO','en_GB');

-- -----------------------------------------------------
-- Populate users table
-- -----------------------------------------------------
INSERT INTO userSettings
    (userSetting_userId, userSetting_postsPerPage)
VALUES
    ((SELECT user_id FROM users WHERE user_username='admin'), '10'),
    ((SELECT user_id FROM users WHERE user_username='user'), '10');

-- -----------------------------------------------------
-- Populate userGroups table
-- -----------------------------------------------------
INSERT INTO userGroups (userGroup_name) VALUES
('admin'),
('moderator'),
('user');

-- -----------------------------------------------------
-- Populate usersInUserGroup table
-- -----------------------------------------------------
INSERT INTO userInUserGroup
(userInUserGroup_userId, userInUserGroup_userGroupId)
VALUES
((SELECT user_id      FROM users      WHERE user_username='admin'),
 (SELECT userGroup_id FROM userGroups WHERE userGroup_name='admin'));
