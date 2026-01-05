package http.request;

public enum HttpMethod {

    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    PATCH,
    ;

    public boolean isGet() {
        return this == HttpMethod.GET;
    }

    public boolean isPost() {
        return this == HttpMethod.POST;
    }
}
