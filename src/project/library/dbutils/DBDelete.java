package project.library.dbutils;

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

    public String generateSQL() throws GenerateException
    {
        if (tableName == null)
        {
            throw new GenerateException("Delete table is empty!");
        }
        if (valueConditionMap.isEmpty())
        {
            throw new GenerateException("Delete condition is empty!");
        }
        return String.format("DELETE FROM `%s` %s", tableName, generateConditionSQL());
    }
}
