package application.repository.impl.h2;

import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import db.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleImageH2Database implements ArticleImageRepository {

    private final Logger logger = LoggerFactory.getLogger(ArticleImageH2Database.class);

    private final JdbcTemplate jdbcTemplate;

    public ArticleImageH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ArticleImage save(ArticleImage articleImage) {
        String sql = "insert into article_images(id, article_id, image_url) values(?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, articleImage.getId(), articleImage.getArticleId(), articleImage.getImageUrl());
        logger.info("Article image saved with id {}", articleImage.getId());
        return articleImage;
    }
}
