package application.dto.response;

import java.util.List;

public record CommentResponse(
        int count,
        List<CommentContentResponse> contents
) {
}
