package webserver.argumentresolver;

import java.util.List;
import java.util.Map;

public class MultipartFiles {

    private final Map<String, List<MultipartFile>> values;

    public MultipartFiles(Map<String, List<MultipartFile>> values) {
        this.values = values;
    }

    public List<MultipartFile> getFiles(String name) {
        return values.getOrDefault(name, List.of());
    }

    public String getFirstFileValue(String name) {
        List<MultipartFile> files = values.getOrDefault(name, List.of());
        if(files.isEmpty()) {
            return null;
        }
        return files.get(0).getValue();
    }
}
