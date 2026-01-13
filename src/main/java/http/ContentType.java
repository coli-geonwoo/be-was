package http;

import java.util.List;
import java.util.Optional;

public enum ContentType {

    HTML(List.of(".html"), "text/html;charset=utf-8"),
    CSS(List.of(".css"),  "text/css;charset=utf-8"),
    JAVA_SCRIPT(List.of(".js"), "application/javascript;charset=utf-8"),
    PNG(List.of(".png"), "image/png"),
    JPG(List.of(".jpg"), "jpeg"),
    SVG(List.of(".svg"), "image/svg+xml"),
    GIF(List.of(".gif"), "image/gif"),
    ICO(List.of(".ico"), "image/x-icon"),
    FORM_URLENCODED(List.of(), "application/x-www-form-urlencoded");
    ;

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";

    private final List<String> extensions;
    private final String responseContentType;

    ContentType(List<String> extensions, String responseContentType) {
        this.extensions = extensions;
        this.responseContentType = responseContentType;
    }

    public static Optional<String> mapToType(String path) {
        for(ContentType contentType : ContentType.values()) {
            for(String extension : contentType.extensions) {
                if(path.endsWith(extension)) {
                    return Optional.of(contentType.responseContentType);
                }
            }
        }
        return Optional.empty();
    }

    public String getResponseContentType() {
        return responseContentType.toLowerCase();
    }
}
