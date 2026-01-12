package db;

import db.h2.JdbcProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);
    private static final JdbcProperties jdbcProperties = JdbcProperties.h2();

    public static void intialize() {
        try(
        Connection conn = DriverManager.getConnection(
                jdbcProperties.url(),
                jdbcProperties.user(),
                jdbcProperties.password()
        )) {
            SqlRunner.initialize(conn, "schema.sql");
            logger.info("Database initialized");
        } catch(Exception exception) {
            logger.error(exception.getMessage(), exception);
            logger.error("DB initialization failed", exception);
        }
    }
}
