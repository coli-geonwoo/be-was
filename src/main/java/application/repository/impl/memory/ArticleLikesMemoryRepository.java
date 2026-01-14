package application.repository.impl.memory;

import application.model.ArticleLikes;
import application.repository.ArticleLikesRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleLikesMemoryRepository implements ArticleLikesRepository {

    private final Map<Long, AtomicLong> likesData = new HashMap<>();

    @Override
    public ArticleLikes save(ArticleLikes articleLikes) {
        likesData.put(articleLikes.getArticleId(), new AtomicLong(articleLikes.getCount()));
        return articleLikes;
    }

    @Override
    public Optional<ArticleLikes> findByArticleId(long articleId) {
        if(likesData.get(articleId) == null) {
            return Optional.empty();
        }
        return Optional.of(new ArticleLikes(articleId, likesData.get(articleId).get()));
    }
}
