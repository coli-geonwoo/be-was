package db;

import java.sql.Connection;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    public static void intialize() {
        try(
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        )) {
            SqlRunner.run(conn, "schema.sql");
            logger.info("Database initialized");
        } catch(Exception exception) {
            logger.error(exception.getMessage(), exception);
            logger.error("DB initialization failed", exception);
        }
    }
}
