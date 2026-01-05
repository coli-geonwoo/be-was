package http.response;

import java.util.Map;

public class Cookie {

    private final Map<String, String> contents;
    private final String path;

    public Cookie(Map<String, String> contents, String path) {
        this.contents = contents;
        this.path = path;
    }

    public Map<String, String> getContents() {
        return contents;
    }

    public String getPath() {
        return path;
    }
}
