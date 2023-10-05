package project.library.dbutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

abstract class DBCondition extends DBTable
{
    protected Map<String, List<Object>> valueConditionMap;
    protected List<DBRange> rangeConditionList;
    protected List<DBMatch> matchConditionList;

    public DBCondition()
    {
        super();
        this.valueConditionMap = new HashMap<>();
        this.rangeConditionList = new ArrayList<>();
        this.matchConditionList = new ArrayList<>();
    }

    public DBCondition(String tableName)
    {
        super(tableName);
        this.valueConditionMap = new HashMap<>();
        this.rangeConditionList = new ArrayList<>();
        this.matchConditionList = new ArrayList<>();
    }

    public void addValueCondition(String name, Object value)
    {
        if (valueConditionMap.containsKey(name))
        {
            valueConditionMap.get(name).add(value);
        }
        else
        {
            List<Object> valueList = new ArrayList<>();

            valueList.add(value);
            valueConditionMap.put(name, valueList);
        }
    }

    public void addRangeCondition(String name, Object minValue, Object maxValue)
    {
        rangeConditionList.add(new DBRange(name, minValue, maxValue));
    }

    public void addMatchCondition(String name, String pattern)
    {
        matchConditionList.add(new DBMatch(name, pattern));
    }

    private String generateValueConditionItemSQL(String name)
    {
        StringBuilder updateConditionItemSQL = new StringBuilder();

        for (Object value : valueConditionMap.get(name))
        {
            if (!updateConditionItemSQL.isEmpty())
            {
                updateConditionItemSQL.append(" OR ");
            }
            updateConditionItemSQL.append(String.format("`%s` = '%s'", name, value));
        }
        return String.format("(%s)", updateConditionItemSQL);
    }

    protected String generateConditionSQL() throws SQLException
    {
        if (valueConditionMap.isEmpty() && rangeConditionList.isEmpty() && matchConditionList.isEmpty())
        {
            return "";
        }
        StringBuilder updateConditionSQL = new StringBuilder();
   
        for (String name : valueConditionMap.keySet())
        {
            if (!updateConditionSQL.isEmpty())
            {
                updateConditionSQL.append(" AND ");
            }
            updateConditionSQL.append(generateValueConditionItemSQL(name));
        }
        for (DBRange range : rangeConditionList)
        {
            if (!updateConditionSQL.isEmpty())
            {
                updateConditionSQL.append(" AND ");
            }
            updateConditionSQL.append(range.generateSQL());
        }
        for (DBMatch match : matchConditionList)
        {
            if (!updateConditionSQL.isEmpty())
            {
                updateConditionSQL.append(" AND ");
            }
            updateConditionSQL.append(match.generateSQL());
        }
        return String.format("WHERE %s", updateConditionSQL);
    }
}
