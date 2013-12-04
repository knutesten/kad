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
-- Create postsInThread table
-- -----------------------------------------------------

CREATE TABLE postsInThread (
  postsInThread_postId             INT           NOT NULL,
  postsInThread_threadId           INT           NOT NULL,

  PRIMARY KEY (postsInThread_postId, postsInThread_threadId),
  FOREIGN KEY (postsInThread_postId)
    REFERENCES posts(post_id),
  FOREIGN KEY (postsInThread_threadId)
    REFERENCES threads(thread_id)
);
