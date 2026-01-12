package application.model;

import application.exception.CustomException;
import application.exception.ErrorCode;

public class Article {

    private final Long id;
    private final String title;
    private final String content;
    private final String userId;

    public Article(Long id, String title, String content, String userId) {
        validate(title);
        validate(content);
        validate(userId);
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Article(String title, String content, String userId) {
        this(null, title, content, userId);
    }

    private void validate(String value) {
        if(value == null || value.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_ARTICLE_INPUT);
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
