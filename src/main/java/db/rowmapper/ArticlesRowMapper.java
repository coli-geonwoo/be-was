package db.rowmapper;

import application.model.Article;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticlesRowMapper implements RowMapper<List<Article>> {

    private final RowMapper<Article> articleRowMapper = new SelectArticleRowMapper();

    @Override
    public List<Article> mapRow(ResultSet rs) {
        try {
            List<Article> articles = new ArrayList<>();
            while (rs.next()) {
                articles.add(articleRowMapper.mapRow(rs));
            }
            return articles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
