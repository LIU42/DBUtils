package project.library.dbutils;

import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;

public class DBUpdate extends DBCondition implements DBUtils
{
    private Map<String, Object> updateValueMap;

    public DBUpdate()
    {
        super();
        this.updateValueMap = new HashMap<>();
    }

    public DBUpdate(String tableName)
    {
        super(tableName);
        this.updateValueMap = new HashMap<>();
    }

    public void addUpdateValue(String name, Object value)
    {
        updateValueMap.put(name, value);
    }

    private String generateValueSQL()
    {
        StringBuilder updateValueSQL = new StringBuilder();

        for (String name : updateValueMap.keySet())
        {
            if (!updateValueSQL.isEmpty())
            {
                updateValueSQL.append(", ");
            }
            updateValueSQL.append(String.format("`%s` = '%s'", name, updateValueMap.get(name)));
        }
        return updateValueSQL.toString();
    }

    public String generateSQL() throws SQLException
    {
        if (tableName == null)
        {
            throw new SQLException("Update table is empty!");
        }
        if (updateValueMap.isEmpty())
        {
            throw new SQLException("Update value is empty!");
        }
        if (valueConditionMap.isEmpty())
        {
            throw new SQLException("Update condition is empty!");
        }
        String updateValueSQL = generateValueSQL();
        String updateConditionSQL = generateConditionSQL();

        return String.format("UPDATE `%s` SET %s %s", tableName, updateValueSQL, updateConditionSQL);
    }
}
