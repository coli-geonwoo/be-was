package webserver.http.request;

import java.util.stream.Stream;

public enum HttpVersion {

    HTTP_1_1(1.1),
    ;

    private static final String HTTP_VERSION_PREFIX = "HTTP/";

    private final double version;

    HttpVersion(double version) {
        this.version = version;
    }

    public static HttpVersion mapToHttpVersion(String version) {
        double versionNumber = parseVersion(version);
        return Stream.of(values())
                .filter(httpVersion -> httpVersion.getVersion() == versionNumber)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown version: " + version));
    }

    private static double parseVersion(String version) {
        try {
            return Double.parseDouble(version.substring(HTTP_VERSION_PREFIX.length()));
        }catch(NumberFormatException e) {
            throw new IllegalArgumentException("Unknown version: " + version);
        }
    }

    public double getVersion() {
        return version;
    }
}
