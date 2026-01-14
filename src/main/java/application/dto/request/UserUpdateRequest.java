package application.dto.request;

import java.util.List;
import javax.annotation.Nullable;
import webserver.argumentresolver.MultipartFile;
import webserver.argumentresolver.MultipartFiles;

public record UserUpdateRequest(
        @Nullable String nickname,
        @Nullable String password,
        @Nullable MultipartFile image,
        boolean deleteProfile
) {

    public UserUpdateRequest(MultipartFiles multipartFiles) {
        this(
                multipartFiles.getFirstFileValue("nickname"),
                multipartFiles.getFirstFileValue("password"),
                getMultipartFile(multipartFiles, "profileImage"),
                Boolean.parseBoolean(multipartFiles.getFirstFileValue("deleteProfile"))
        );
    }

    private static MultipartFile getMultipartFile(MultipartFiles multipartFiles, String fileName) {
        List<MultipartFile> files = multipartFiles.getFiles(fileName);
        if (files.isEmpty()) {
            return null;
        }
        return files.get(0);
    }
}
