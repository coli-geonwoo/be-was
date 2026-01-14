package application.model;

import java.util.UUID;

public class Comment {

    private final String id;
    private final long articleId;
    private final String userId;
    private final String content;

    public Comment(String id, long articleId, String userId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
    }

    public Comment(long articleId, String userId, String content) {
        this(UUID.randomUUID().toString(), articleId, userId, content);
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
