package application.config.argumentresolver;

import application.repository.ArticleRepository;
import application.repository.SessionRepository;
import application.repository.UserRepository;
import db.ArticleMemoryDatabase;
import db.UserMemoryDatabase;
import db.SessionMemoryDatabase;

public class RepositoryConfig {

    private RepositoryConfig() {}

    public static ArticleRepository articleRepository() {
        return new ArticleMemoryDatabase();
    }

    public static UserRepository userRepository() {
        return new UserMemoryDatabase();
    }

    public static SessionRepository sessionRepository() {
        return new SessionMemoryDatabase();
    }
}
