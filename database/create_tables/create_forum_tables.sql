-- -------------------------------------------------------------------------
-- Create topic table
-- -------------------------------------------------------------------------
CREATE TABLE categories (
  category_id                 INT          NOT NULL AUTO_INCREMENT,
  category_name               VARCHAR(255) NOT NULL UNIQUE,
  category_lastUpdatedTopicId INT,
  PRIMARY KEY (category_id)
);

-- -------------------------------------------------------------------------
-- Create topic table
-- -------------------------------------------------------------------------
CREATE TABLE topics (
  topic_id           INT          NOT NULL AUTO_INCREMENT,
  topic_createdBy    INT          NOT NULL,
  topic_createdTime  BIGINT       NOT NULL,
  topic_categoryId   INT          NOT NULL,
  topic_title        VARCHAR(255) NOT NULL UNIQUE,

  PRIMARY KEY (topic_id),
  FOREIGN KEY (topic_createdBy)
    REFERENCES users(user_id),
  FOREIGN KEY (topic_categoryId)
    REFERENCES categories(category_Id)
);

-- -------------------------------------------------------------------------
-- Create post table
-- -------------------------------------------------------------------------
CREATE TABLE posts (
  post_id             INT           NOT NULL AUTO_INCREMENT,
  post_createdBy      INT           NOT NULL,
  post_createdTime    BIGINT        NOT NULL,
  post_lastEditedBy   INT           NOT NULL,
  post_lastEditedTime BIGINT        NOT NULL,
  post_content        TEXT          NOT NULL,

  PRIMARY KEY (post_id),
  FOREIGN KEY (post_createdBy)
    REFERENCES users(user_id),
  FOREIGN KEY (post_lastEditedBy)
    REFERENCES users(user_id)

 );

-- -------------------------------------------------------------------------
-- Create postInTopic table
-- -------------------------------------------------------------------------
CREATE TABLE postInTopic (
  postInTopic_postId            INT NOT NULL,
  postInTopic_topicId           INT NOT NULL,
  postInTopic_postNumberInTopic INT NOT NULL,

  PRIMARY KEY (postInTopic_postId, postInTopic_topicId),
  UNIQUE (postInTopic_topicId, postInTopic_postNumberInTopic),
  FOREIGN KEY (postInTopic_postId)
    REFERENCES posts(post_id),
  FOREIGN KEY (postInTopic_topicId)
    REFERENCES topics(topic_id)
);

-- -------------------------------------------------------------------------
-- Add foreign key constraint to Categories table for the last updated topic
-- -------------------------------------------------------------------------
ALTER TABLE categories ADD
  FOREIGN KEY (category_lastUpdatedTopicId)
    REFERENCES topics(topic_id);

-- -------------------------------------------------------------------------
-- Add index on topic_categoryId in topics
-- -------------------------------------------------------------------------
CREATE INDEX topic_createdTime on topics(topic_createdTime desc);
