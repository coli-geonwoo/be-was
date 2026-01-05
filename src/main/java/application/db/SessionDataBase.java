package application.db;

import java.util.HashMap;
import java.util.Map;

public class SessionDataBase {

    private static final Map<String, String> sessionData = new HashMap<>();

    private SessionDataBase() {
    }

    public static String getData(String sessionId) {
        return sessionData.get(sessionId);
    }

    public static void saveData(String sessionId, String data) {
        sessionData.put(sessionId, data);
    }
}
