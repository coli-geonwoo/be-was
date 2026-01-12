package db.h2;

public record JdbcProperties(
        String url,
        String user,
        String password
) {

    public static JdbcProperties h2() {
        return new JdbcProperties(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );
    }
}
