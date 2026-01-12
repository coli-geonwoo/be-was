package db.rowmapper;

import java.sql.ResultSet;

public interface RowMapper <T> {

    T mapRow(ResultSet rs);
}
