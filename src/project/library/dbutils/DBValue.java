package project.library.dbutils;

import java.sql.SQLException;

class DBValue implements DBUtils
{
    private String name;
    private Object value;

    public DBValue(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public Object getValue()
    {
        return value;
    }

    public String generateSQL() throws SQLException
    {
        return String.format("`%s` = '%s'", name, value);
    }
}
