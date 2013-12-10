CREATE TABLE categories (
  category_id    INT,
  category_name  VARCHAR(255)  NOT NULL UNIQUE,
  PRIMARY KEY (category_id)
);

CREATE TABLE topicInCategory (
  topicInCategory_categoryId INT NOT NULL,
  topicInCategory_topicId    INT NOT NULL,

  PRIMARY KEY (topicInCategory_categoryId, topicInCategory_topicId),
  FOREIGN KEY (topicInCategory_categoryId)
    REFERENCES categories(category_id),
  FOREIGN KEY (topicInCategory_topicId)
    REFERENCES topics(topic_id)
);
