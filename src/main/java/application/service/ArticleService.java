package application.service;

import application.config.argumentresolver.RepositoryConfig;
import application.dto.request.ArticleCreateRequest;
import application.model.Article;
import application.model.User;
import application.repository.ArticleRepository;

public class ArticleService {

    private final ArticleRepository articleRepository = RepositoryConfig.articleRepository();

    public Article save(User user, ArticleCreateRequest articleCreateRequest) {
        Article article = new Article(
                articleCreateRequest.getTitle(),
                articleCreateRequest.getContent(),
                user.getUserId()
        );
        return articleRepository.save(article);
    }
}
