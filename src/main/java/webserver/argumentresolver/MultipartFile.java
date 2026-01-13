package webserver.argumentresolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MultipartFile {

    private final byte [] files;

    public MultipartFile(byte[] files) {
        this.files = files;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(files);
    }
}
