package project.library.dbutils;

import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;

public class DBInsert extends DBTable implements DBUtils {

    private Map<String, DBValue> insertValueMap;

    public DBInsert() {
        super();
        this.insertValueMap = new HashMap<>();
    }

    public DBInsert(String tableName) {
        super(tableName);
        this.insertValueMap = new HashMap<>();
    }

    public void addInsertValue(String name, Object value) {
        insertValueMap.put(name, new DBValue(name, value));
    }

    public String generateSQL() throws SQLException {
        if (tableName == null) {
            throw new SQLException("Insert table is empty!");
        }
        if (insertValueMap.isEmpty()) {
            throw new SQLException("Insert value is empty!");
        }
        StringBuilder insertNameSQL = new StringBuilder();
        StringBuilder insertValueSQL = new StringBuilder();
        for (String name : insertValueMap.keySet()) {
            DBValue dataValue = insertValueMap.get(name);
            if (!insertNameSQL.isEmpty() && !insertValueSQL.isEmpty()) {
                insertNameSQL.append(", ");
                insertValueSQL.append(", ");
            }
            insertNameSQL.append(String.format("`%s`", dataValue.getName()));
            insertValueSQL.append(String.format("'%s'", dataValue.getValue()));
        }
        return String.format("INSERT INTO `%s` (%s) VALUES (%s)", tableName, insertNameSQL, insertValueSQL);
    }
}
