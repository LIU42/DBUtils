# DBUtils

一个 Java 数据库操作小工具，可以由条件生成对应的 SQL 语句，可以解决条件数量不定情况下的数据库操作问题，基本覆盖了常用的数据库操作。

#### 工具特点

- 统一数据库操作中的各种参数，无需针对不同的业务需求编写不同的 SQL 语句。

- 仅由不同的条件生成 SQL 语句，可以方便地嵌入原生的 JDBC 程序。

#### 组件介绍

- DBUtils  --  数据库操作类统一接口

- DBInsert  --  数据库插入操作

- DBQuery  --  数据库查询操作

- DBUpdate  --  数据库更新操作

- DBDelete  --  数据库删除操作

#### 使用示例

```java
DBQuery query = new DBQuery("table_name");

query.addSelectName("column1");
query.addSelectName("column2");

query.addValueCondition("name1", "value1");
query.addValueCondition("name2", "value2");
query.addRangeCondition("name3", "min", "max");

Connection connection = getConnection();
Statement statement = connection.createStatement(query.generateSQL());

// 生成的 SQL 语句
// SELECT column1, column2 FROM table_name WHERE (name2 = value2) AND (name1 = value1) AND (name3 BETWEEN min AND max)

ResultSet resultSet = statement.executeQuery();
```


