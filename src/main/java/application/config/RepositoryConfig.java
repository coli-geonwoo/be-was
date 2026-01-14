package application.config;

import application.repository.ArticleImageRepository;
import application.repository.ArticleLikesRepository;
import application.repository.ArticleRepository;
import application.repository.CommentRepository;
import application.repository.SessionRepository;
import application.repository.UserRepository;
import application.repository.impl.h2.ArticleH2Database;
import application.repository.impl.h2.ArticleImageH2Database;
import application.repository.impl.h2.CommentH2Database;
import application.repository.impl.h2.UserH2Database;
import application.repository.impl.memory.ArticleLikesMemoryRepository;
import application.repository.impl.memory.SessionMemoryDatabase;
import application.repository.rowmapper.SelectCommentsRowMapper;
import db.JdbcProperties;
import db.JdbcTemplate;

public class RepositoryConfig {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcProperties.h2());

    private RepositoryConfig() {
    }

    public static ArticleRepository articleRepository() {
        return new ArticleH2Database(jdbcTemplate);
    }

    public static UserRepository userRepository() {
        return new UserH2Database(jdbcTemplate);
    }

    public static SessionRepository sessionRepository() {
        return new SessionMemoryDatabase();
    }

    public static ArticleImageRepository articleImageRepository() {
        return new ArticleImageH2Database(jdbcTemplate);
    }

    public static ArticleLikesRepository articleLikesRepository() {
        return new ArticleLikesMemoryRepository();
    }

    public static CommentRepository commentRepository() {
        return new CommentH2Database(jdbcTemplate);
    }
}
