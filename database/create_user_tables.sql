-- -----------------------------------------------------
-- Create users table
-- -----------------------------------------------------
CREATE TABLE users (
  user_id			 INT		   NOT NULL	AUTO_INCREMENT,
  user_username      VARCHAR(30)   NOT NULL	UNIQUE,
  user_email         VARCHAR(100)  NOT NULL	UNIQUE,
  user_hash          VARCHAR(255)  NOT NULL,
  user_salt          VARCHAR(255)  NOT NULL,
  user_fullName      VARCHAR(255),
  user_country       VARCHAR(255),
  user_locale        VARCHAR(50),

  PRIMARY KEY (user_id)
);

-- -----------------------------------------------------
-- Create userGroups table
-- ----------------------------------------------------

CREATE TABLE userGroups(
  userGroup_userGroup    VARCHAR(100),

  PRIMARY KEY (userGroup_userGroup)
);

-- -----------------------------------------------------
-- Create usersInUserGroup table
-- -----------------------------------------------------

CREATE TABLE usersInUserGroup(
  usersInUserGroup_userId     	int,
  usersInUserGroup_userGroup    VARCHAR(100),

  PRIMARY KEY (usersInUserGroup_userId, usersInUserGroup_userGroup),
  FOREIGN KEY (usersInUserGroup_userId)
    REFERENCES users(user_id),
  FOREIGN KEY (usersInUserGroup_userGroup)
    REFERENCES userGroups(userGroup_userGroup)
);
