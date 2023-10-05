package project.library.dbutils;

import java.sql.SQLException;

class DBRange implements DBUtils
{
    private String name;
    private Object minValue;
    private Object maxValue;

    public DBRange(String name, Object minValue, Object maxValue)
    {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String generateSQL() throws SQLException
    {
        if (minValue != null && maxValue != null)
        {
            return String.format("(`%s` BETWEEN '%s' AND '%s')", name, minValue, maxValue);
        }
        if (minValue != null)
        {
            return String.format("(`%s` >= '%s')", name, minValue);
        }
        if (maxValue != null)
        {
            return String.format("(`%s` <= '%s')", name, maxValue);
        }
        throw new SQLException("Range bounds is empty!");
    }
}
