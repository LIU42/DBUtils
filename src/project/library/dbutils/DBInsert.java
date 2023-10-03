package project.library.dbutils;

import java.util.HashMap;
import java.util.Map;

public class DBInsert extends DBTable implements DBUtils
{
    private Map<String, Object> insertValueMap;

    public DBInsert()
    {
        super();
        this.insertValueMap = new HashMap<>();
    }

    public DBInsert(String tableName)
    {
        super(tableName);
        this.insertValueMap = new HashMap<>();
    }

    public void addInsertValue(String name, Object value)
    {
        insertValueMap.put(name, value);
    }

    public String generateSQL() throws GenerateException
    {
        if (tableName == null)
        {
            throw new GenerateException("Insert table is empty!");
        }
        if (insertValueMap.isEmpty())
        {
            throw new GenerateException("Insert value is empty!");
        }
        StringBuilder insertNameSQL = new StringBuilder();
        StringBuilder insertValueSQL = new StringBuilder();

        for (String name : insertValueMap.keySet())
        {
            if (!insertNameSQL.isEmpty() && !insertValueSQL.isEmpty())
            {
                insertNameSQL.append(", ");
                insertValueSQL.append(", ");
            }
            insertNameSQL.append(String.format("`%s`", name));
            insertValueSQL.append(String.format("'%s'", insertValueMap.get(name)));
        }
        return String.format("INSERT INTO `%s` (%s) VALUES (%s)", tableName, insertNameSQL, insertValueSQL);
    }
}
