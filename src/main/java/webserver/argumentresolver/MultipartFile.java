package webserver.argumentresolver;

import http.ContentType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MultipartFile {

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.PLAIN_TEXT;

    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final byte[] content;
    private final boolean isFormField;

    public MultipartFile(String name, String value) {
        this.name = name;
        this.originalFilename = null;
        this.contentType = DEFAULT_CONTENT_TYPE.getResponseContentType();
        this.content = value.getBytes(StandardCharsets.UTF_8);
        this.isFormField = true;
    }

    public MultipartFile(
            String name,
            String originalFilename,
            String contentType,
            byte[] content
    ) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.content = content;
        this.isFormField = false;
    }

    public String getName() {
        return name;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    public long getSize() {
        if (content != null) {
            return content.length;
        }
        return 0;
    }

    public byte[] getBytes() {
        return content;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    public boolean isFormField() {
        return isFormField;
    }

    public String getValue() {
        if (!isFormField) {
            throw new IllegalStateException("Not a form field");
        }
        return new String(content, StandardCharsets.UTF_8);
    }
}
