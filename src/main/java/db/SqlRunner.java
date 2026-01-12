package db;

import db.h2.JdbcProperties;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlRunner {

    private static final Logger logger = LoggerFactory.getLogger(SqlRunner.class);
    private static final JdbcProperties jdbcProperties = JdbcProperties.h2();

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

    public static Object executeQuery(Function<Connection, Object> query) {
        try(Connection conn = DriverManager.getConnection(
                        jdbcProperties.url(),
                        jdbcProperties.user(),
                        jdbcProperties.password()
        )){
            return query.apply(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int executeUpdate(Function<Connection, Integer> query) {
        try(Connection conn = DriverManager.getConnection(
                jdbcProperties.url(),
                jdbcProperties.user(),
                jdbcProperties.password()
        )){
            return query.apply(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

