CREATE TABLE IF NOT EXISTS users (
    user_id   VARCHAR(50)  PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS articles (
        id       BIGINT AUTO_INCREMENT PRIMARY KEY,
        title    VARCHAR(200)  NOT NULL,
        content  CLOB          NOT NULL,
        user_id  VARCHAR(50)   NOT NULL
);

