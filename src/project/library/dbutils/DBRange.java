package project.library.dbutils;

class DBRange
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

    public String generateRangeSQL() throws GenerateException
    {
        if (minValue != null && maxValue != null)
        {
            return String.format("(%s BETWEEN %s AND %s)", name, minValue, maxValue);
        }
        if (minValue != null)
        {
            return String.format("(%s >= %s)", name, minValue);
        }
        if (maxValue != null)
        {
            return String.format("(%s <= %s)", name, maxValue);
        }
        throw new GenerateException("Range bounds is empty!");
    }
}
