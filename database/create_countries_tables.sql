-- -----------------------------------------------------
-- Create countries table
-- -----------------------------------------------------
CREATE TABLE countries (
  country_code        VARCHAR(2)    NOT NULL,
  country_name        VARCHAR(200)  NOT NULL,
  PRIMARY KEY (country_code)
);