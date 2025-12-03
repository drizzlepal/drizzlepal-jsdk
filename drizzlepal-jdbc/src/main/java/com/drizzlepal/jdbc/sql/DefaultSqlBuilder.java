package com.drizzlepal.jdbc.sql;

import com.drizzlepal.jdbc.metadata.TableMetaData;
import com.drizzlepal.utils.StringUtils;

public abstract class DefaultSqlBuilder implements SqlBuilder {

    private final StringBuilder builder = new StringBuilder();

    protected String escape(String keyword) {
        return "`" + keyword + "`";
    }

    @Override
    public String CreateTable(TableMetaData tableMetaData) {
        builder.delete(0, builder.length());
        builder.append(CREATE);
        if (StringUtils.isNotBlank(tableMetaData.getDatabase())) {
            builder.append(escape(tableMetaData.getDatabase())).append(".").append(escape(tableMetaData.getName()));
        } else {
            builder.append(escape(tableMetaData.getName()));
        }
        builder.append(SPACE).append(OPEN_PARENTHESIS);
        tableMetaData.getColumns().stream().forEachOrdered(column -> {

        });
        return builder.toString();
    }

    @Override
    public String Delete() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String Insert() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String Select() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String TruncateTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String Update() {
        // TODO Auto-generated method stub
        return null;
    }

    private static final String SELECT = "SELECT";

    private static final String DISTINCT = "DISTINCT";

    private static final String AS = "AS";

    private static final String COUNT = "COUNT";

    private static final String COUNT_ALL = "*";

    private static final String COUNT_DISTINCT = "COUNT(DISTINCT";

    private static final String GROUP_BY = "GROUP BY";

    private static final String ORDER_BY = "ORDER BY";

    private static final String HAVING = "HAVING";

    private static final String IN = "IN";

    private static final String FROM = "FROM";

    private static final String WHERE = "WHERE";

    private static final String SET = "SET";

    private static final String UPDATE = "UPDATE";

    private static final String DELETE = "DELETE";

    private static final String INSERT = "INSERT";

    private static final String INTO = "INTO";

    private static final String VALUES = "VALUES";

    private static final String AND = "AND";

    private static final String OR = "OR";

    private static final String EQUAL = "=";

    private static final String SPACE = " ";

    private static final String COMMA = ",";

    private static final String PARAM_PLACEHOLDER = "?";

    private static final String DOT = ".";

    private static final String SINGLE_QUOTE = "'";

    private static final String OPEN_PARENTHESIS = "(";

    private static final String CLOSE_PARENTHESIS = ") ";

    private static final String CREATE = "CREATE ";

}
