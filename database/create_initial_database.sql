-- -----------------------------------------------------
-- Create kad schema
-- -----------------------------------------------------

CREATE SCHEMA kad DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE kad;

-- -----------------------------------------------------
-- Create users table
-- -----------------------------------------------------
CREATE TABLE users(
  users_username    VARCHAR(30),
  users_email       VARCHAR(100)  NOT NULL   UNIQUE ,
  user_password     VARCHAR(255)  NOT NULL,
  user_salt         VARCHAR(255)  NOT NULL,
  user_fullname     VARCHAR(255)  NOT NULL,
  user_country      VARCHAR(255)  NOT NULL,
  user_countryCode  VARCHAR(50)   NOT NULL,

  PRIMARY KEY (users_username)
);

-- -----------------------------------------------------
-- Create userGroups table
-- ----------------------------------------------------

CREATE TABLE userGroups(
  userGroups_userGroup    VARCHAR(100),

  PRIMARY KEY (userGroups_userGroup)
);

-- -----------------------------------------------------
-- Create userInUserGroup table
-- -----------------------------------------------------

CREATE TABLE userInUserGroup(
  usersInUserGroup_username     VARCHAR(30),
  usersInUserGroup_userGroup    VARCHAR(100),

  PRIMARY KEY (usersInUserGroup_username, usersInUserGroup_userGroup),
  FOREIGN KEY (usersInUserGroup_username)
    REFERENCES users(users_username),
  FOREIGN KEY (usersInUserGroup_userGroup)
    REFERENCES userGroups(userGroups_userGroup)
)
