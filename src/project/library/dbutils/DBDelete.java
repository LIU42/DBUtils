package project.library.dbutils;

import java.sql.SQLException;

public class DBDelete extends DBCondition implements DBUtils
{
    public DBDelete()
    {
        super();
    }

    public DBDelete(String tableName)
    {
        super(tableName);
    }

    public String generateSQL() throws SQLException
    {
        if (tableName == null)
        {
            throw new SQLException("Delete table is empty!");
        }
        if (valueConditionMap.isEmpty())
        {
            throw new SQLException("Delete condition is empty!");
        }
        return String.format("DELETE FROM `%s` %s", tableName, generateConditionSQL());
    }
}
