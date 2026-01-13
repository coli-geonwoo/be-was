package application.repository.impl.h2;

import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import db.JdbcTemplate;

public class ArticleImageH2Database implements ArticleImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleImageH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ArticleImage save(ArticleImage articleImage) {
        String sql = "insert into article_image(id, article_id, image_url) values(?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, articleImage.getId(), articleImage.getArticleId(), articleImage.getImageUrl());
        return articleImage;
    }
}
