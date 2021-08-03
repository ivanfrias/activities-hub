DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS authors;

CREATE TABLE IF NOT EXISTS authors (
  id                  VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
  name                VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS messages (
  id                        VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  text                      VARCHAR      NOT NULL,
  author                    VARCHAR(60) NOT NULL,
  FOREIGN KEY (author) REFERENCES authors (id)
);


