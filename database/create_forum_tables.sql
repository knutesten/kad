-- -----------------------------------------------------
-- Create topic table
-- -----------------------------------------------------
CREATE TABLE threads (
  thread_id           INT          NOT NULL AUTO_INCREMENT,
  thread_title        VARCHAR(255) NOT NULL,
  thread_createdBy    VARCHAR(30)  NOT NULL,
  thread_dateCreated  BIGINT       NOT NULL,
  thread_category     VARCHAR(255) NOT NULL,

  PRIMARY KEY (thread_id)
);
