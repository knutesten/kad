-- -----------------------------------------------------
-- Create countries table
-- -----------------------------------------------------
CREATE TABLE countries (
  country_code        CHAR(2)    NOT NULL,
  country_name        VARCHAR(200)  NOT NULL,
  PRIMARY KEY (country_code)
);
