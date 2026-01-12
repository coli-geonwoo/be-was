package db;

import application.repository.SessionRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDataBase implements SessionRepository {

    private static final Map<String, String> sessionData = new ConcurrentHashMap<>();

    @Override
    public Optional<String> getData(String sessionId) {
        return Optional.ofNullable(sessionData.get(sessionId));
    }

    @Override
    public void removeData(String sessionId) {
        sessionData.remove(sessionId);
    }

    @Override
    public void saveData(String sessionId, String data) {
        sessionData.put(sessionId, data);
    }

    @Override
    public void clear() {
        sessionData.clear();
    }
}
