package application.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploader {

    public FileUploader() {
    }

    public void imageUpload(InputStream inputStream, String path) {
        try {
            Path uploadPath = Paths.get(path);
            if(!Files.exists(uploadPath)) {
               Files.createDirectories(uploadPath);
            }
            Files.copy(inputStream, uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image", e);
        }
    }
}
