package project.library.dbutils;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class DBQuery extends DBCondition implements DBUtils
{
    private List<String> selectNameList;
    private List<DBOrder> orderItemList;
    private boolean isDistinct;

    public DBQuery()
    {
        super();
        this.selectNameList = new ArrayList<>();
        this.orderItemList = new ArrayList<>();
        this.isDistinct = false;
    }

    public DBQuery(String tableName)
    {
        super(tableName);
        this.selectNameList = new ArrayList<>();
        this.orderItemList = new ArrayList<>();
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

        for (String name : selectNameList)
        {
            if (!selectNameSQL.isEmpty())
            {
                selectNameSQL.append(", ");
            }
            selectNameSQL.append(String.format("`%s`", name));
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
            orderItemSQL.append(orderItem.generateSQL());
        }
        return String.format("ORDER BY %s", orderItemSQL);
    }

    public String generateSQL() throws SQLException
    {
        if (tableName == null)
        {
            throw new SQLException("Query table is empty!");
        }
        String conditionSQL = generateConditionSQL();
        String selectSQL = generateSelectSQL();
        String orderSQL = generateOrderSQL();

        return String.format("%s FROM `%s` %s %s", selectSQL, tableName, conditionSQL, orderSQL);
    }
}
