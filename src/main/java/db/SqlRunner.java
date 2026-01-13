package db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlRunner {

    private static final Logger logger = LoggerFactory.getLogger(SqlRunner.class);

    private SqlRunner() {
    }

    public static void initialize(Connection connection, String resourcePath) {
        try (
                InputStream inputStream = SqlRunner.class
                        .getClassLoader()
                        .getResourceAsStream(resourcePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                Statement stmt = connection.createStatement()
        ) {
            if (inputStream == null) {
                throw new IllegalStateException(resourcePath + " not found");
            }

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("--") || line.isEmpty()) {
                    continue;
                }

                sql.append(line).append(' ');

                if (line.endsWith(";")) {
                    stmt.execute(sql.toString());
                    logger.info("Executed SQL: {}", sql);
                    sql.setLength(0);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to execute " + resourcePath, e);
        }
    }
}

