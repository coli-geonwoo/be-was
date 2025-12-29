package webserver.view;


import java.io.UnsupportedEncodingException;

public class View {

    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private final byte[] content;

    public View(String file) {
        try {
            this.content = file.getBytes(DEFAULT_CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("파일을 파싱할 수 없습니다");
        }
    }
}
