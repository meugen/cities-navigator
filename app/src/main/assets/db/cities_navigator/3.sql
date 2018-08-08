CREATE TEMPORARY TABLE __temp (
  name VARCHAR(40) NOT NULL,
  code VARCHAR(10) NOT NULL,
  country_group VARCHAR(20) NOT NULL,
  states TEXT NOT NULL);
INSERT INTO __temp SELECT name, code, country_group, states FROM countries;
DROP TABLE countries;
CREATE TABLE countries (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(40) NOT NULL,
  code VARCHAR(10) NOT NULL,
  country_group VARCHAR(20) NOT NULL,
  states TEXT NOT NULL);
CREATE UNIQUE INDEX unq_countries_code ON countries (code);
INSERT INTO countries (name, code, country_group, states) SELECT name, code, country_group, states FROM __temp;
DROP TABLE __temp;