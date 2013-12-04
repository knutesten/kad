-- -----------------------------------------------------
-- Create users table
-- -----------------------------------------------------
CREATE TABLE users (
  user_id			 INT		   NOT NULL	AUTO_INCREMENT,
  user_username      VARCHAR(30)   NOT NULL	UNIQUE,
  user_email         VARCHAR(255)  NOT NULL	UNIQUE,
  user_hash          VARCHAR(255)  NOT NULL,
  user_salt          VARCHAR(255)  NOT NULL,
  user_fullName      VARCHAR(255),
  user_country       VARCHAR(2),
  user_locale        VARCHAR(50),
  PRIMARY KEY (user_id),
  FOREIGN KEY (user_country)
    REFERENCES countries(country_code)
);

-- -----------------------------------------------------
-- Create userGroups table
-- ----------------------------------------------------

CREATE TABLE userGroups(
  userGroup_id           INT          NOT NULL AUTO_INCREMENT,
  userGroup_userGroup    VARCHAR(100),

  PRIMARY KEY (userGroup_id)
);

-- -----------------------------------------------------
-- Create usersInUserGroup table
-- -----------------------------------------------------

CREATE TABLE usersInUserGroup(
  usersInUserGroup_userId     	INT,
  usersInUserGroup_userGroupId  INT,

  PRIMARY KEY (usersInUserGroup_userId, usersInUserGroup_userGroupId),
  FOREIGN KEY (usersInUserGroup_userId)
    REFERENCES users(user_id),
  FOREIGN KEY (usersInUserGroup_userGroupId)
    REFERENCES userGroups(userGroup_id)
);
