package application.repository;

import java.util.Optional;

public interface SessionRepository {

    Optional<String> getData(String sessionId);

    void removeData(String sessionId);

    void saveData(String sessionId, String data);

    void clear();
}
