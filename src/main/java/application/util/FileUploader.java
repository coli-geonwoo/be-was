package application.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploader {

    private static final Path UPLOAD_PATH = Paths.get("src", "main", "resources", "static", "uploads", "images");

    public String imageUpload(InputStream inputStream, String imageFileName) {
        try {
            String fileName = UUID.randomUUID() + "_" + imageFileName;
            Path fullPath = UPLOAD_PATH.resolve(fileName);
            Path parentDir = fullPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.copy(inputStream, fullPath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image", e);
        }
    }
}
