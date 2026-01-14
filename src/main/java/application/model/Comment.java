package application.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {

    private final String id;
    private final long articleId;
    private final String userId;
    private final String content;
    private final LocalDateTime createdAt;

    public Comment(String id, long articleId, String userId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(long articleId, String userId, String content, LocalDateTime createdAt) {
        this(UUID.randomUUID().toString(), articleId, userId, content, createdAt);
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }
}
