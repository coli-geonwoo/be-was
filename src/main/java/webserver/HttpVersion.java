package webserver;

import java.util.stream.Stream;

public enum HttpVersion {

    HTTP_1_1(1.1),
    ;

    private final double version;

    HttpVersion(double version) {
        this.version = version;
    }

    public HttpVersion mapToHttpVersion(double version) {
        return Stream.of(values())
                .filter(httpVersion -> httpVersion.getVersion() == version)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown version: " + version));
    }

    public double getVersion() {
        return version;
    }
}
