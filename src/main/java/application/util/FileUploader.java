package application.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploader {

    public FileUploader() {
    }

    public void imageUpload(InputStream inputStream, Path path) {
        try {
            Path parentDir = path.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image", e);
        }
    }
}
