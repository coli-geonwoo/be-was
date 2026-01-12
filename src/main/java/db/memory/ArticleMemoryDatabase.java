package db.memory;

import application.model.Article;
import application.repository.ArticleRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleMemoryDatabase implements ArticleRepository {

    private static final Logger logger = LoggerFactory.getLogger(ArticleMemoryDatabase.class);
    private static final AtomicLong counter = new AtomicLong();
    private static final Map<Long, Article> articleData = new ConcurrentHashMap<>();

    @Override
    public Article save(Article article) {
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

    @Override
    public List<Article> findUserById(String userId) {
        return articleData.values()
                .stream()
                .filter(article -> article.isOwn(userId))
                .toList();
    }

    @Override
    public void clear() {
        articleData.clear();
    }
}
