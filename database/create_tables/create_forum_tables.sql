-- -----------------------------------------------------
-- Create topic table
-- -----------------------------------------------------
CREATE TABLE topics (
  topic_id           INT          NOT NULL AUTO_INCREMENT,
  topic_title        VARCHAR(255) NOT NULL UNIQUE,
  topic_createdBy    VARCHAR(30)  NOT NULL,
  topic_createdTime  BIGINT       NOT NULL,

  PRIMARY KEY (topic_id),
  FOREIGN KEY (topic_createdBy)
    REFERENCES users(user_username)
);

-- -----------------------------------------------------
-- Create post table
-- -----------------------------------------------------
CREATE TABLE posts (
  post_id             INT           NOT NULL AUTO_INCREMENT,
  post_createdBy      VARCHAR(30)   NOT NULL,
  post_createdTime    BIGINT        NOT NULL,
  post_lastEditedBy   VARCHAR(30),
  post_lastEditedTime BIGINT,
  post_content        TEXT          NOT NULL,

  PRIMARY KEY (post_id),
  FOREIGN KEY (post_createdBy)
    REFERENCES users(user_username),
  FOREIGN KEY (post_lastEditedBy)
    REFERENCES users(user_username)

 );

-- -----------------------------------------------------
-- Create postInTopic table
-- -----------------------------------------------------

CREATE TABLE postInTopic (
  postInTopic_postId    INT NOT NULL,
  postInTopic_topicId   INT NOT NULL,

  PRIMARY KEY (postInTopic_postId, postInTopic_topicId),
  FOREIGN KEY (postInTopic_postId)
    REFERENCES posts(post_id),
  FOREIGN KEY (postInTopic_topicId)
    REFERENCES topics(topic_id)
);
