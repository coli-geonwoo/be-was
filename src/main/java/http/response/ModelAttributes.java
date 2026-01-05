package http.response;

import java.util.HashMap;
import java.util.Map;

public class ModelAttributes {

    private final Map<String, String> attributes;

    public ModelAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void put(String key, String value) {
        attributes.put(key, value);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
