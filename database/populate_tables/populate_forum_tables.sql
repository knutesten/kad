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
  ('10','admin','300','admin','12213123','This is another testpost'),
  ('11','admin','400','admin','450','This is another testpost');
  
INSERT INTO posts
  (post_id, post_createdBy, post_createdTime, post_content)
VALUES
  ('12', 'admin', '1000', 'Hest 1'),
  ('13', 'admin', '1000', 'Hest 2'),
  ('14', 'admin', '1000', 'Hest 3'),
  ('15', 'admin', '1000', 'Hest 4'),
  ('16', 'admin', '1000', 'Hest 5'),
  ('17', 'admin', '1000', 'Hest 6'),
  ('18', 'admin', '1000', 'Hest 7'),
  ('19', 'admin', '1000', 'Hest 8'),
  ('20', 'admin', '1000', 'Hest 9'),
  ('21', 'admin', '1000', 'Hest 10'),
  ('22', 'admin', '1000', 'Hest 10'),
  ('23', 'admin', '1000', 'Hest 10'),
  ('24', 'admin', '1000', 'Hest 10'),
  ('25', 'admin', '1000', 'Hest 10'),
  ('26', 'admin', '1000', 'Hest 10'),
  ('27', 'admin', '1000', 'Hest 10'),
  ('28', 'admin', '1000', 'Hest 10'),
  ('29', 'admin', '1000', 'Hest 10'),
  ('30', 'admin', '1000', 'Hest 10'),
  ('31', 'admin', '1000', 'Hest 10'),
  ('32', 'admin', '1000', 'Hest 10'),
  ('33', 'admin', '1000', 'Hest 10'),
  ('34', 'admin', '1000', 'Hest 10'),
  ('35', 'admin', '1000', 'Hest 10'),
  ('36', 'admin', '1000', 'Hest 10'),
  ('37', 'admin', '1000', 'Hest 10'),
  ('38', 'admin', '1000', 'Hest 10'),
  ('39', 'admin', '1000', 'Hest 10'),
  ('40', 'admin', '1000', 'Hest 10'),
  ('41', 'admin', '1000', 'Hest 10'),
  ('42', 'admin', '1000', 'Hest 10'),
  ('43', 'admin', '1000', 'Hest 10'),
  ('44', 'admin', '1000', 'Hest 10'),
  ('45', 'admin', '1000', 'Hest 10'),
  ('46', 'admin', '1000', 'Hest 10'),
  ('47', 'admin', '1000', 'Hest 10'),
  ('48', 'admin', '1000', 'Hest 10'),
  ('49', 'admin', '1000', 'Hest 10');

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
  ('10','2','1'),
  ('11','1','11'),
  ('12','6','1'),
  ('13','6','2'),
  ('14','6','3'),
  ('15','6','4'),
  ('16','6','6'),
  ('17','6','9'),
  ('18','6','10'),
  ('19','6','11'),
  ('20','6','12'),
  ('21','6','13'),
  ('22','3','1'),
  ('23','4','1'),
  ('24','5','1'),
  ('25','7','1'),
  ('26','8','1'),
  ('27','9','1'),
  ('28','10','1'),
  ('29','11','1'),
  ('30','12','1'),
  ('31','13','1'),
  ('32','14','1'),
  ('33','15','1'),
  ('34','16','1'),
  ('35','17','1'),
  ('36','18','1'),
  ('37','19','1'),
  ('38','20','1'),
  ('39','21','1'),
  ('40','22','1'),
  ('41','23','1'),
  ('42','24','1'),
  ('43','25','1'),
  ('44','26','1'),
  ('45','27','1'),
  ('46','28','1'),
  ('47','29','1'),
  ('48','30','1'),
  ('49','31','1');

