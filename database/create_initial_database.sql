-- -----------------------------------------------------
-- Create kad schema
-- -----------------------------------------------------

CREATE SCHEMA kad DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE kad;

-- -----------------------------------------------------
-- Create users table
-- -----------------------------------------------------
CREATE TABLE users(
  users_id			  int			NOT NULL	AUTO_INCREMENT,
  users_username      VARCHAR(30)   NOT NULL	UNIQUE,
  users_email         VARCHAR(100)  NOT NULL	UNIQUE,
  users_password      VARCHAR(255)  NOT NULL,
  users_salt          VARCHAR(255)  NOT NULL,
  users_fullName      VARCHAR(255)  NOT NULL,
  users_country       VARCHAR(255)  NOT NULL,
  users_locale        VARCHAR(50)   NOT NULL,

  PRIMARY KEY (users_id)
);

-- -----------------------------------------------------
-- Create userGroups table
-- ----------------------------------------------------

CREATE TABLE userGroups(
  userGroups_userGroup    VARCHAR(100),

  PRIMARY KEY (userGroups_userGroup)
);

-- -----------------------------------------------------
-- Create usersInUserGroup table
-- -----------------------------------------------------

CREATE TABLE usersInUserGroup(
  usersInUserGroup_userId     	int,
  usersInUserGroup_userGroup    VARCHAR(100),

  PRIMARY KEY (usersInUserGroup_userId, usersInUserGroup_userGroup),
  FOREIGN KEY (usersInUserGroup_userId)
    REFERENCES users(users_id),
  FOREIGN KEY (usersInUserGroup_userGroup)
    REFERENCES userGroups(userGroups_userGroup)
);
