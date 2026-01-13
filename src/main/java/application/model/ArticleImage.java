package application.model;

import java.util.UUID;

public class ArticleImage {

    private final String id;
    private final long articleId;
    private final String imageUrl;

    public ArticleImage(String id, long articleId, String imageUrl) {
        this.id = id;
        this.articleId = articleId;
        this.imageUrl = imageUrl;
    }

    public ArticleImage(long articleId, String imageUrl) {
        this(UUID.randomUUID().toString(), articleId, imageUrl);
    }

    public String getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
