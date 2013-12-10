USE kad;
-- -----------------------------------------------------
-- Populate topics table
-- -----------------------------------------------------

INSERT INTO topics
  (topic_id, topic_title, topic_createdBy, topic_createdTime)
VALUES
  ('1','Test Topic','admin','10');

-- -----------------------------------------------------
-- Populate posts table
-- -----------------------------------------------------

INSERT INTO posts 
  (post_id, post_createdBy, post_createdTime, post_lastEditedBy, post_lastEditedTime, post_content) 
VALUES
  ('1','admin','10','admin','15','This is a testpost!');

-- -----------------------------------------------------
-- Populate postInTopic table
-- -----------------------------------------------------

INSERT INTO postInTopic
  (postInTopic_postId, postInTopic_topicId, postInTopic_postNumberInTopic)
VALUES
  ('1','1','1')
