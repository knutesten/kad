-- -----------------------------------------------------
-- Create kad schema
-- -----------------------------------------------------
CREATE SCHEMA kad DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE kad;

SOURCE create_countries_tables.sql;
SOURCE create_user_tables.sql;
SOURCE create_forum_tables.sql;
SOURCE create_category_tables.sql;
