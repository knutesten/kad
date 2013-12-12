USE kad;
-- -----------------------------------------------------
-- Populate topics table
-- -----------------------------------------------------

INSERT INTO topics
  (topic_id, topic_title, topic_createdBy, topic_createdTime)
VALUES
  ('1','Test Topic','admin','10'),
  ('2','New Test Topic','admin','300'),
  ('3','Gris1 Topic','admin','100'),
  ('4','Gris2 Topic','admin','200'),
  ('5','Gris3 Topic','admin','3000'),
  ('6','Hest1 Topic','admin','100'),
  ('7','Hest2 Topic','admin','200'),
  ('8','Hest3 Topic','admin','300'),
  ('9','Kamel1 Topic','admin','100'),
  ('10','Kamel2 Topic','admin','200'),
  ('11','Kamel3 Topic','admin','300'),  
  ('12','Ku1 Topic','admin','100'),
  ('13','Ku2 Topic','admin','200'),
  ('14','Ku3 Topic','admin','300'),
  ('15','Høne1 Topic','admin','100'),
  ('16','Høne2 Topic','admin','200'),
  ('17','Høne3 Topic','admin','300'),
  ('18','Høne4 Topic','admin','400'),
  ('19','Høne5 Topic','admin','500'),
  ('20','Høne6 Topic','admin','600'), 
  ('21','Høne7 Topic','admin','700'),
  ('22','Høne8 Topic','admin','800'),
  ('23','Høne9 Topic','admin','900'),
  ('24','Høne10 Topic','admin','1000'),
  ('25','Høne11 Topic','admin','1100'),
  ('26','Høne12 Topic','admin','1200'),
  ('27','Hest4 Topic','admin','400'),
  ('28','Hest5 Topic','admin','500'),
  ('29','Hest6 Topic','admin','600'),
  ('30','Hest7 Topic','admin','700'),
  ('31','Hest8 Topic','admin','800'),
  ('32','Hest9 Topic','admin','900'),
  ('33','Hest10 Topic','admin','1000'),
  ('34','Hest11 Topic','admin','1100'),
  ('35','Hest12 Topic','admin','1200'),
  ('36','Hest13 Topic','admin','1300'),
  ('37','Hest14 Topic','admin','1400'),
  ('38','Hest15 Topic','admin','1500');

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

