package application.model;

import application.exception.CustomException;
import application.exception.ErrorCode;
import java.time.LocalDateTime;

public class Article {

    private final Long id;
    private final String title;
    private final String content;
    private final String userId;
    private final LocalDateTime creationDate;

    public Article(Long id, String title, String content, String userId, LocalDateTime creationDate) {
        validate(title);
        validate(content);
        validate(userId);
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.creationDate = creationDate;
    }

    public Article(String title, String content, String userId, LocalDateTime creationDate) {
        this(null, title, content, userId, creationDate);
    }

    private void validate(String value) {
        if(value == null) {
            throw new CustomException(ErrorCode.INVALID_ARTICLE_INPUT);
        }
    }

    public boolean isOwn(String userId) {
        return userId.equals(this.userId);
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
