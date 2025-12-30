package webserver.view;


import java.io.UnsupportedEncodingException;

public class View {

    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private final byte[] content;

    public View(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
