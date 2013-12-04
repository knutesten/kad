-- -----------------------------------------------------
-- Create topic table
-- -----------------------------------------------------
CREATE TABLE threads (
  thread_id           INT          NOT NULL AUTO_INCREMENT,
  thread_title        VARCHAR(255) NOT NULL,
  thread_createdBy    VARCHAR(30)  NOT NULL,
  thread_createdTime  BIGINT       NOT NULL,
  thread_category     VARCHAR(255) NOT NULL,

  PRIMARY KEY (thread_id)
);

-- -----------------------------------------------------
-- Create post table
-- -----------------------------------------------------
CREATE TABLE posts (
  post_id             INT           NOT NULL AUTO_INCREMENT,
  post_createdBY      VARCHAR(255)  NOT NULL,
  post_dateCreated    BIGINT        NOT NULL,

  PRIMARY KEY (post_id)

 );

-- -----------------------------------------------------
-- Create postInThread table
-- -----------------------------------------------------

CREATE TABLE postsInThread (
  postInThread_postId             INT           NOT NULL,
  postInThread_threadId           INT           NOT NULL,

  PRIMARY KEY (postInThread_postId, postInThread_threadId),
  FOREIGN KEY (postInThread_postId)
    REFERENCES posts(post_id),
  FOREIGN KEY (postInThread_threadId)
    REFERENCES threads(thread_id)
);
