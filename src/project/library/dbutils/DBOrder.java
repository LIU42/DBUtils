package project.library.dbutils;

class DBOrder
{
    private String name;
    private boolean isReverse;

    public DBOrder(String name)
    {
        this.name = name;
        this.isReverse = false;
    }

    public DBOrder(String name, boolean isReverse)
    {
        this.name = name;
        this.isReverse = isReverse;
    }

    public String generateOrderSQL()
    {
        if (isReverse)
        {
            return String.format("%s DESC", name);
        }
        return String.format("%s ASC", name);
    }
}
