package application.repository;

import application.model.ArticleImage;
import java.util.List;

public interface ArticleImageRepository {

    ArticleImage save(ArticleImage articleImage);

    List<ArticleImage> findByArticleId(long articleId);
}
