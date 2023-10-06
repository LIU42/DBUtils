package project.library.dbutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

abstract class DBCondition extends DBTable
{
    protected Map<String, List<DBUtils>> valueConditionMap;
    protected Map<String, List<DBUtils>> rangeConditionMap;
    protected Map<String, List<DBUtils>> matchConditionMap;

    public DBCondition()
    {
        super();
        this.valueConditionMap = new HashMap<>();
        this.rangeConditionMap = new HashMap<>();
        this.matchConditionMap = new HashMap<>();
    }

    public DBCondition(String tableName)
    {
        super(tableName);
        this.valueConditionMap = new HashMap<>();
        this.rangeConditionMap = new HashMap<>();
        this.matchConditionMap = new HashMap<>();
    }

    private void addConditionItem(Map<String, List<DBUtils>> conditionMap, String name, DBUtils dataItem)
    {
        if (conditionMap.containsKey(name))
        {
            conditionMap.get(name).add(dataItem);
        }
        else
        {
            List<DBUtils> dataItemList = new ArrayList<>();

            dataItemList.add(dataItem);
            conditionMap.put(name, dataItemList);
        }
    }

    public void addValueCondition(String name, Object value)
    {
        addConditionItem(valueConditionMap, name, new DBValue(name, value));
    }

    public void addRangeCondition(String name, Object minValue, Object maxValue)
    {
        addConditionItem(valueConditionMap, name, new DBRange(name, minValue, maxValue));
    }

    public void addMatchCondition(String name, String pattern)
    {
        addConditionItem(valueConditionMap, name, new DBMatch(name, pattern));
    }

    private String generateConditionItemSQL(Map<String, List<DBUtils>> conditionMap, String name) throws SQLException
    {
        StringBuilder conditionItemSQL = new StringBuilder();

        for (DBUtils dataItem : conditionMap.get(name))
        {
            if (!conditionItemSQL.isEmpty())
            {
                conditionItemSQL.append(" OR ");
            }
            conditionItemSQL.append(dataItem.generateSQL());
        }
        return String.format("(%s)", conditionItemSQL);
    }

    private void generateConditionTypeSQL(StringBuilder conditionSQL, Map<String, List<DBUtils>> conditionMap) throws SQLException
    {
        for (String name : conditionMap.keySet())
        {
            if (!conditionSQL.isEmpty())
            {
                conditionSQL.append(" AND ");
            }
            conditionSQL.append(generateConditionItemSQL(conditionMap, name));
        }
    }

    protected String generateConditionSQL() throws SQLException
    {
        if (valueConditionMap.isEmpty() && rangeConditionMap.isEmpty() && matchConditionMap.isEmpty())
        {
            return "";
        }
        StringBuilder conditionSQL = new StringBuilder();

        generateConditionTypeSQL(conditionSQL, valueConditionMap);
        generateConditionTypeSQL(conditionSQL, rangeConditionMap);
        generateConditionTypeSQL(conditionSQL, matchConditionMap);

        return String.format("WHERE %s", conditionSQL);
    }
}
