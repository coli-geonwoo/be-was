package http.response;

import java.util.List;

public enum ContentType {

    HTML(List.of(".html"), "text/html;charset=utf-8"),
    CSS(List.of(".css"),  "text/css;charset=utf-8"),
    JAVA_SCRIPT(List.of(".js"), "application/javascript;charset=utf-8"),
    PNG(List.of(".png"), "image/png"),
    JPG(List.of(".jpg"), "jpeg"),
    SVG(List.of(".svg"), "image/svg+xml"),
    GIF(List.of(".gif"), "image/gif"),
    ICO(List.of(".ico"), "image/x-icon");

    private final List<String> extensions;
    private final String responseContentType;

    ContentType(List<String> extensions, String responseContentType) {
        this.extensions = extensions;
        this.responseContentType = responseContentType;
    }

    public static String mapToType(String path) {
        for(ContentType contentType : ContentType.values()) {
            for(String extension : contentType.extensions) {
                if(path.endsWith(extension)) {
                    return contentType.responseContentType;
                }
            }
        }
        throw new IllegalArgumentException("No content type found for path: " + path);
    }
}
