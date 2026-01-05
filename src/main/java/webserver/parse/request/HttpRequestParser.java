package webserver.parse.request;

interface HttpRequestParser <T> {

    T parse(String input);
}
