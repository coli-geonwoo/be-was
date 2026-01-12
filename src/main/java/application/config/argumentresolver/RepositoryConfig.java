package application.config.argumentresolver;

import application.repository.ArticleRepository;
import application.repository.SessionRepository;
import application.repository.UserRepository;
import db.ArticleDatabase;
import db.Database;
import db.SessionDataBase;

public class RepositoryConfig {

    private RepositoryConfig() {}

    public static ArticleRepository articleRepository() {
        return new ArticleDatabase();
    }

    public static UserRepository userRepository() {
        return new Database();
    }

    public static SessionRepository sessionRepository() {
        return new SessionDataBase();
    }
}
