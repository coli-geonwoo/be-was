package db;

import application.model.Article;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleDatabase {

    private static final AtomicLong counter = new AtomicLong();
    private static final Map<Long, Article> articleData = new HashMap<>();

    private ArticleDatabase() {}

    public static Article save(Article article) {
        long id = counter.incrementAndGet();
        Article savedArticle = new Article(
                id,
                article.getTitle(),
                article.getContent(),
                article.getUserId()
        );
        articleData.put(id, article);
        return savedArticle;
    }
}
