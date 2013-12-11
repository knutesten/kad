USE kad;
-- -----------------------------------------------------
-- Populate topics table
-- -----------------------------------------------------

INSERT INTO topics
  (topic_id, topic_title, topic_createdBy, topic_createdTime)
VALUES
  ('1','Test Topic','admin','10'),
  ('2', 'New Test Topic','admin','300'),
  ('3','Gris1 Topic','admin','100'),
  ('4','Gris2 Topic','admin','200'),
  ('5','Gris3 Topic','admin','3000');

-- -----------------------------------------------------
-- Populate posts table
-- -----------------------------------------------------

INSERT INTO posts
  (post_id, post_createdBy, post_createdTime, post_lastEditedBy, post_lastEditedTime, post_content)
VALUES
  ('1','admin','10','admin','1000','This is a testpost 1!'),
  ('2','admin','15','admin','100015','This is a testpost 2!'),
  ('3','admin','30','admin','1000215','This is a testpost 3!'),
  ('4','admin','50','admin','1000415','This is a testpost 4!'),
  ('5','admin','60','admin','1000615','This is a testpost 5!'),
  ('6','admin','80','admin','1000715','This is a testpost 6!'),
  ('7','admin','90','admin','1000815','This is a testpost 7!'),
  ('8','admin','100','admin','1000915','This is a testpost 8!'),
  ('9','admin','200','admin','1100025','This is a testpost 9!'),
  ('10','admin','300','admin','12213123','This is another testpost');



-- -----------------------------------------------------
-- Populate postInTopic table
-- -----------------------------------------------------

INSERT INTO postInTopic
  (postInTopic_postId, postInTopic_topicId, postInTopic_postNumberInTopic)
VALUES
  ('1','1','1'),
  ('2','1','2'),
  ('3','1','3'),
  ('4','1','4'),
  ('5','1','5'),
  ('6','1','20'),
  ('7','1','7'),
  ('8','1','8'),
  ('9','1','9'),
  ('10','2','1');

