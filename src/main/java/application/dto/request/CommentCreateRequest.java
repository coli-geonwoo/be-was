package application.dto.request;

public class CommentCreateRequest {

    private long articleId;
    private String content;

    public CommentCreateRequest(long articleId) {
        this.articleId = articleId;
    }

    private CommentCreateRequest() {}

    public String getContent() {
        return content;
    }

    public long getArticleId() {
        return articleId;
    }
}
