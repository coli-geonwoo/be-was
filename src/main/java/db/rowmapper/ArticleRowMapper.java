package db.rowmapper;

import application.model.Article;
import java.sql.ResultSet;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs) {
        try {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            String userId = rs.getString("user_id");
            return new Article(id, title, content, userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map article row", e);
        }
    }
}
