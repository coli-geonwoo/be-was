package application.model;

public class ArticleLikes {

    private final Long id;
    private final long articleId;
    private final long count;

    public ArticleLikes(Long id, long articleId, long count) {
        this.id = id;
        this.articleId = articleId;
        this.count = count;
    }

    public ArticleLikes(long articleId, long count) {
        this(null, articleId, count);
    }

    public long getArticleId() {
        return articleId;
    }

    public long getCount() {
        return count;
    }

    public Long getId() {
        return id;
    }
}
