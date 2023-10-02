package project.library.dbutils;

abstract class DBTable
{
    protected String tableName;

    public DBTable()
    {
        this.tableName = null;
    }

    public DBTable(String tableName)
    {
        this.tableName = tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
    }
}
