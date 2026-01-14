CREATE TABLE IF NOT EXISTS users (
    user_id   VARCHAR(50)  PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS articles (
        id       BIGINT AUTO_INCREMENT PRIMARY KEY,
        title    VARCHAR(200)  NOT NULL,
        content  CLOB          NOT NULL,
        user_id  VARCHAR(50)   NOT NULL,
        created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS article_images (
    id          VARCHAR(36)   PRIMARY KEY,
    article_id  BIGINT        NOT NULL,
    image_url   VARCHAR(500)  NOT NULL
);

CREATE TABLE IF NOT EXISTS comments (
    id          VARCHAR(36)   PRIMARY KEY,
    article_id  BIGINT        NOT NULL,
    user_id     VARCHAR(500)  NOT NULL,
    content      VARCHAR(500) NOT NULL,
);
