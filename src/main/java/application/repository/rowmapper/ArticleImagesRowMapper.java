package application.repository.rowmapper;

import application.model.ArticleImage;
import db.RowMapper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticleImagesRowMapper implements RowMapper<List<ArticleImage>> {

    @Override
    public List<ArticleImage> mapRow(ResultSet rs) {
        try {
            List<ArticleImage> images = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                long articleId = rs.getLong("article_id");
                String imageUrl = rs.getString("image_url");
                images.add(new ArticleImage(id, articleId, imageUrl));
            }
            return images;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
