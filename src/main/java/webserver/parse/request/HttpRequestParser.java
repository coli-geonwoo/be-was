package webserver.parse.request;

public interface HttpRequestParser <T> {

    T parse(String input);
}
