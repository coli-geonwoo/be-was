package application.repository;

import application.model.Article;
import java.util.List;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findUserById(String userId);

    void clear();
}
