package application.service;

import application.dto.request.ArticleCreateRequest;
import application.model.Article;
import application.model.User;
import db.ArticleDatabase;

public class ArticleService {

    public Article save(User user, ArticleCreateRequest articleCreateRequest) {
        Article article = new Article(
                articleCreateRequest.getTitle(),
                articleCreateRequest.getContent(),
                user.getUserId()
        );
        return ArticleDatabase.save(article);
    }
}
