package application.repository;

import application.model.ArticleLikes;
import java.util.Optional;

public interface ArticleLikesRepository {

    ArticleLikes save(ArticleLikes articleLikes);

    Optional<ArticleLikes> findByArticleId(long articleId);
}
