package project.library.dbutils;

class DBMatch implements DBUtils
{
    private String name;
    private String pattern;

    public DBMatch(String name, String pattern)
    {
        this.name = name;
        this.pattern = pattern;
    }

    public String generateSQL()
    {
        return String.format("(`%s` LIKE '%%%s%%')", name, pattern);
    }
}
