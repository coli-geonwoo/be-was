package application.service;

import application.config.RepositoryConfig;
import application.dto.request.ArticleCreateRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.Article;
import application.model.ArticleLikes;
import application.model.User;
import application.repository.ArticleLikesRepository;
import application.repository.ArticleRepository;
import java.time.LocalDateTime;

public class ArticleService {

    private final ArticleRepository articleRepository = RepositoryConfig.articleRepository();
    private final ArticleLikesRepository articleLikesRepository = RepositoryConfig.articleLikesRepository();

    public Article save(User user, ArticleCreateRequest articleCreateRequest) {
        Article article = new Article(
                articleCreateRequest.getTitle(),
                articleCreateRequest.getContent(),
                user.getUserId(),
                LocalDateTime.now()
        );
        Article savedArticle = articleRepository.save(article);
        articleLikesRepository.save(new ArticleLikes(savedArticle.getId(), 0L));
        return savedArticle;
    }

    public int count() {
        return articleRepository.count();
    }

    public Article findLatestArticle(int offset) {
        return articleRepository.getLatestArticle(offset);
    }

    public long countArticleLikes(long articleId) {
        return articleLikesRepository.findByArticleId(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_LIKES_NOT_FOUND))
                .getCount();
    }

    public long incrementAndGetLikes(long articleId) {
        return articleLikesRepository.incrementAndGet(articleId);
    }
}
