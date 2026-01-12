package db;

import application.model.Article;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleDatabase {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);
    private static final AtomicLong counter = new AtomicLong();
    private static final Map<Long, Article> articleData = new HashMap<>();

    private ArticleDatabase() {
    }

    public static Article save(Article article) {
        long id = counter.incrementAndGet();
        Article savedArticle = new Article(
                id,
                article.getTitle(),
                article.getContent(),
                article.getUserId()
        );
        articleData.put(id, savedArticle);
        logger.info("saved article: {}", savedArticle);
        return savedArticle;
    }

    public static List<Article> findUserById(String userId) {
        return articleData.values()
                .stream()
                .filter(article -> article.isOwn(userId))
                .toList();
    }
}
