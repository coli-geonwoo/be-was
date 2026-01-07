package application.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionDataBase {

    private static final Map<String, String> sessionData = new HashMap<>();

    private SessionDataBase() {
    }

    public static Optional<String> getData(String sessionId) {
        return Optional.ofNullable(sessionData.get(sessionId));
    }

    public static void removeData(String sessionId) {
        sessionData.remove(sessionId);
    }

    public static void saveData(String sessionId, String data) {
        sessionData.put(sessionId, data);
    }

    public static void clear() {
        sessionData.clear();
    }
}
