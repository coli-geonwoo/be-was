package db.h2;

import application.model.Article;
import application.repository.ArticleRepository;
import java.util.List;

public class ArticleH2Databse implements ArticleRepository {

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public List<Article> findUserById(String userId) {
        return List.of();
    }

    @Override
    public void clear() {

    }
}
