package project.library.dbutils;

import java.util.ArrayList;
import java.util.List;

public class DBQuery extends DBCondition implements DBUtils
{
    private List<String> selectNameList;
    private List<DBOrder> orderItemList;
    private String groupName;
    private boolean isDistinct;

    public DBQuery()
    {
        super();
        this.selectNameList = new ArrayList<>();
        this.orderItemList = new ArrayList<>();
        this.groupName = null;
        this.isDistinct = false;
    }

    public DBQuery(String tableName)
    {
        super(tableName);
        this.selectNameList = new ArrayList<>();
        this.orderItemList = new ArrayList<>();
        this.groupName = null;
        this.isDistinct = false;
    }

    public void addSelectName(String name)
    {
        selectNameList.add(name);
    }

    public void addOrderItem(String name)
    {
        orderItemList.add(new DBOrder(name));
    }

    public void addOrderItem(String name, boolean isReverse)
    {
        orderItemList.add(new DBOrder(name, isReverse));
    }

    public void setGroupName(String name)
    {
        this.groupName = name;
    }

    public void setDistinct(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    private String generateSelectNameSQL()
    {
        if (selectNameList.isEmpty())
        {
            return "*";
        }
        StringBuilder selectNameSQL = new StringBuilder();

        for (String value : selectNameList)
        {
            if (!selectNameSQL.isEmpty())
            {
                selectNameSQL.append(", ");
            }
            selectNameSQL.append(value);
        }
        return selectNameSQL.toString();
    }

    private String generateSelectSQL()
    {
        StringBuilder selectNameSQL = new StringBuilder(generateSelectNameSQL());

        if (isDistinct)
        {
            selectNameSQL.append(" DISTINCT");
        }
        return String.format("SELECT %s", selectNameSQL);
    }

    private String generateOrderSQL()
    {
        if (orderItemList.isEmpty())
        {
            return "";
        }
        StringBuilder orderItemSQL = new StringBuilder();

        for (DBOrder orderItem : orderItemList)
        {
            if (!orderItemSQL.isEmpty())
            {
                orderItemSQL.append(", ");
            }
            orderItemSQL.append(orderItem.generateOrderSQL());
        }
        return String.format("ORDER BY %s", orderItemSQL);
    }

    private String generateGroupSQL()
    {
        if (groupName == null)
        {
            return "";
        }
        return String.format("GROUP BY %s", groupName);
    }

    public String generateSQL() throws GenerateException
    {
        if (tableName == null)
        {
            throw new GenerateException("Query table is empty!");
        }
        String conditionSQL = generateConditionSQL();
        String selectSQL = generateSelectSQL();
        String orderSQL = generateOrderSQL();
        String groupSQL = generateGroupSQL();

        return String.format("%s FROM %s %s %s %s", selectSQL, tableName, conditionSQL, orderSQL, groupSQL);
    }
}
