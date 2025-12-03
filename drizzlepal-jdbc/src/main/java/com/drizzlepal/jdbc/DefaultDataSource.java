package com.drizzlepal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.drizzlepal.jdbc.exception.ConnectionOperationException;
import com.drizzlepal.jdbc.exception.UnknownDatabaseException;
import com.drizzlepal.jdbc.metadata.ColumnInfoLabels;
import com.drizzlepal.jdbc.metadata.ColumnMetaData;
import com.drizzlepal.jdbc.metadata.DatabaseMetaData;
import com.drizzlepal.jdbc.metadata.IndexMetaData;
import com.drizzlepal.jdbc.metadata.PrimaryKeyMetaData;
import com.drizzlepal.jdbc.metadata.TableMetaData;
import com.drizzlepal.utils.StringUtils;
import com.drizzlepal.utils.functions.ConsumerThrowable;
import com.zaxxer.hikari.HikariDataSource;

public abstract class DefaultDataSource implements DataSource {

    protected final HikariDataSource dataSource;

    protected final DefaultDatabaseConfig configCommon;

    public DefaultDataSource(DefaultDatabaseConfig configCommon) {
        this.configCommon = configCommon;
        this.dataSource = new HikariDataSource();
        this.dataSource.setJdbcUrl(buildJdbcUrl(configCommon));
        this.dataSource.setUsername(configCommon.getUsername());
        this.dataSource.setPassword(configCommon.getPassword());
        this.dataSource.setMaximumPoolSize(configCommon.getMaxActive());
        this.dataSource.setConnectionTimeout(configCommon.getMaxWaitMs());
        this.dataSource.setMinimumIdle(configCommon.getMinIdle());
        this.dataSource.setDriverClassName(configCommon.getDatabaseType().getDriverClassName());
        this.dataSource.setConnectionInitSql(configCommon.getDatabaseType().getConnectionInitSql());
    }

    @Override
    public void close() throws Exception {
        dataSource.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public void doWithConnection(ConsumerThrowable<Connection> thingsToDo) throws ConnectionOperationException {
        try (Connection connection = this.getConnection()) {
            thingsToDo.accept(connection);
        } catch (Throwable e) {
            throw new ConnectionOperationException(e);
        }
    }

    public abstract String buildJdbcUrl(DefaultDatabaseConfig configCommon);

    @Override
    public List<String> getDatabaseNames() throws UnknownDatabaseException, SQLException {
        LinkedList<String> res = new LinkedList<>();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("show databases;");
                ResultSet executeQuery = statement.executeQuery();) {
            while (executeQuery.next()) {
                res.add(executeQuery.getString(1));
            }
        }
        return res;
    }

    @Override
    public DatabaseMetaData getMetaData() throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getMetaData(configCommon.getSchema(), configCommon.getDatabase());
    }

    @Override
    public DatabaseMetaData getMetaData(String schema, String databaseName) throws SQLException {
        DatabaseMetaData databaseMetaData = new DatabaseMetaData();
        databaseMetaData.setName(databaseName);
        databaseMetaData.setSchema(schema);
        LinkedList<TableMetaData> tableMetaDataList = new LinkedList<>();
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {
                try (PreparedStatement statement = connection
                        .prepareStatement(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql());) {
                    configCommon.getDatabaseType().getDatabaseMetaDataQuerySqlParameterSetter().setParameters(statement,
                            schema, databaseName);
                    HashMap<String, LinkedList<ColumnMetaData>> tableColumnMap = new HashMap<>();
                    try (ResultSet columns = statement.executeQuery();) {
                        while (columns.next()) {
                            String tableName = columns.getString("TABLE_NAME");
                            if (!tableColumnMap.containsKey(tableName)) {
                                tableColumnMap.put(tableName, new LinkedList<>());
                            }
                            tableColumnMap.get(tableName).addLast(readColumnMetaDataFromResultSet(columns));
                        }
                    }
                }
            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();
                HashMap<String, LinkedList<ColumnMetaData>> tableColumnMap = new HashMap<>();
                try (ResultSet columns = metaData.getColumns(databaseName, schema, "%", "%");) {
                    while (columns.next()) {
                        String tableName = columns.getString("TABLE_NAME");
                        if (!tableColumnMap.containsKey(tableName)) {
                            tableColumnMap.put(tableName, new LinkedList<>());
                        }
                        tableColumnMap.get(tableName).addLast(readColumnMetaDataFromResultSet(columns));
                    }
                }
                try (ResultSet tables = metaData.getTables(databaseName, schema, "%",
                        configCommon.getDatabaseType().getDatabaseTableTypes());) {
                    while (tables.next()) {
                        TableMetaData tableMetaData = new TableMetaData();
                        tableMetaData.setDatabase(databaseName);
                        tableMetaData.setName(tables.getString("TABLE_NAME"));
                        tableMetaData.setRemarks(tables.getString("REMARKS"));
                        if (tableColumnMap.containsKey(tableMetaData.getName())) {
                            LinkedList<ColumnMetaData> remove = tableColumnMap.remove(tableMetaData.getName());
                            ArrayList<ColumnMetaData> columns = new ArrayList<ColumnMetaData>(remove.size());
                            remove.stream().sorted((a, b) -> a.getOrdinalPosition() - b.getOrdinalPosition())
                                    .forEachOrdered(c -> columns.add(c));
                            tableMetaData.setColumns(columns);
                        }
                        tableMetaData.setPrimaryKeys(getPrimaryKeys(schema, databaseName, databaseName));
                        tableMetaData.setIndexes(getIndexMetaData(schema, databaseName, databaseName, false));
                        tableMetaDataList.addLast(tableMetaData);
                    }
                }
            }
        }
        databaseMetaData.setTables(new ArrayList<>(tableMetaDataList));
        return databaseMetaData;
    }

    @Override
    public TableMetaData getTableMetaData(String tableName) throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getTableMetaData(configCommon.getSchema(), configCommon.getDatabase(), tableName);
    }

    @Override
    public TableMetaData getTableMetaData(String schema, String databaseName, String tableName) throws SQLException {
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {

            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();
                try (ResultSet tables = metaData.getTables(databaseName, schema, tableName,
                        configCommon.getDatabaseType().getDatabaseTableTypes());) {
                    while (tables.next()) {
                        TableMetaData tableMetaData = new TableMetaData();
                        tableMetaData.setDatabase(databaseName);
                        tableMetaData.setName(tables.getString("TABLE_NAME"));
                        tableMetaData.setRemarks(tables.getString("REMARKS"));
                        tableMetaData.setColumns(getColumnMetaData(schema, databaseName, tableName));
                        tableMetaData.setIndexes(getIndexMetaData(schema, databaseName, tableName, false));
                        tableMetaData.setPrimaryKeys(getPrimaryKeys(schema, databaseName, tableName));
                        return tableMetaData;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<ColumnMetaData> getColumnMetaDataFormSql(String sql) throws SQLException {
        LinkedList<ColumnMetaData> columnMetaDatas = new LinkedList<>();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            ResultSetMetaData metaData = statement.getMetaData();
            if (metaData == null) {
                throw new SQLException("获取来源sql元数据信息失败");
            }
            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setDatatype(metaData.getColumnType(i));
                columnMetaData.setDecimalDigits(metaData.getScale(i));
                columnMetaData.setDefaultValue(null);
                columnMetaData.setLength(metaData.getColumnDisplaySize(i));
                columnMetaData.setName(metaData.getColumnLabel(i));
                columnMetaData.setNullable(null);
                columnMetaData.setOrdinalPosition(i);
                columnMetaData.setRemarks(null);
                columnMetaData.setTypeName(metaData.getColumnTypeName(i));
                columnMetaDatas.addLast(columnMetaData);
            }
        }
        ArrayList<ColumnMetaData> result = new ArrayList<>(columnMetaDatas.size());
        columnMetaDatas.stream().sorted((a, b) -> a.getOrdinalPosition() - b.getOrdinalPosition())
                .forEachOrdered(c -> result.add(c));
        return result;
    }

    @Override
    public ColumnMetaData getColumnMetaData(String tableName, String columnName)
            throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getColumnMetaData(configCommon.getSchema(), configCommon.getDatabase(), tableName, columnName);
    }

    @Override
    public ColumnMetaData getColumnMetaData(String schema, String databaseName, String tableName, String columnName)
            throws SQLException {
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {

            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();
                try (ResultSet columns = metaData.getColumns(databaseName, schema, tableName, columnName);) {
                    while (columns.next()) {
                        return readColumnMetaDataFromResultSet(columns);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<ColumnMetaData> getColumnMetaData(String tableName) throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getColumnMetaData(configCommon.getSchema(), configCommon.getDatabase(), tableName);
    }

    @Override
    public ArrayList<ColumnMetaData> getColumnMetaData(String schema, String databaseName, String tableName)
            throws SQLException {
        LinkedList<ColumnMetaData> columnMetaDataList = new LinkedList<>();
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {

            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();

                try (ResultSet columns = metaData.getColumns(databaseName, schema, tableName,
                        "%");) {
                    while (columns.next()) {
                        columnMetaDataList.addLast(readColumnMetaDataFromResultSet(columns));
                    }
                }
            }
        }
        ArrayList<ColumnMetaData> res = new ArrayList<>();
        columnMetaDataList.stream().sorted((a, b) -> a.getOrdinalPosition() - b.getOrdinalPosition())
                .forEachOrdered(c -> res.add(c));
        return res;
    }

    @Override
    public Map<String, ArrayList<IndexMetaData>> getIndexMetaData(String tableName, boolean unique)
            throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getIndexMetaData(configCommon.getSchema(), configCommon.getDatabase(), tableName, unique);
    }

    @Override
    public Map<String, ArrayList<IndexMetaData>> getIndexMetaData(String schema, String databaseName, String tableName,
            boolean unique)
            throws SQLException {
        HashMap<String, LinkedList<IndexMetaData>> indexColumnMetaDataMap = new HashMap<>();
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {

            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();
                try (ResultSet indexInfo = metaData.getIndexInfo(databaseName, schema, tableName, unique, false);) {
                    while (indexInfo.next()) {
                        IndexMetaData indexMetaData = new IndexMetaData();
                        indexMetaData.setIndexName(indexInfo.getString("INDEX_NAME"));
                        indexMetaData.setType(indexInfo.getString("TYPE"));
                        indexMetaData.setNonUnique(indexInfo.getBoolean("NON_UNIQUE"));
                        indexMetaData.setColumnName(indexInfo.getString("COLUMN_NAME"));
                        indexMetaData.setOrdinalPosition(indexInfo.getInt("ORDINAL_POSITION"));
                        indexMetaData.setAscOrDesc(indexInfo.getString("ASC_OR_DESC"));
                        if (!indexColumnMetaDataMap.containsKey(indexMetaData.getIndexName())) {
                            indexColumnMetaDataMap.put(indexMetaData.getIndexName(), new LinkedList<>());
                        }
                        indexColumnMetaDataMap.get(indexMetaData.getIndexName()).addLast(indexMetaData);
                    }
                }
            }
        }
        if (indexColumnMetaDataMap.size() > 0) {
            return indexColumnMetaDataMap.values().stream().map(linkedList -> {
                ArrayList<IndexMetaData> arrayList = new ArrayList<>(linkedList.size());
                linkedList.stream().sorted((a, b) -> a.getOrdinalPosition() - b.getOrdinalPosition())
                        .forEachOrdered(i -> {
                            arrayList.add(i);
                        });
                return arrayList;
            }).collect(Collectors.toMap(arrayList -> arrayList.get(0).getIndexName(), arrayList -> arrayList));
        } else {
            return Collections.emptyMap();
        }
    }

    @Override
    public ArrayList<PrimaryKeyMetaData> getPrimaryKeys(String tableName)
            throws SQLException, UnknownDatabaseException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getPrimaryKeys(configCommon.getSchema(), configCommon.getDatabase(), tableName);
    }

    @Override
    public ArrayList<PrimaryKeyMetaData> getPrimaryKeys(String schema, String databaseName, String tableName)
            throws SQLException {
        LinkedList<PrimaryKeyMetaData> temp = new LinkedList<>();
        try (Connection connection = getConnection();) {
            if (StringUtils.isNotBlank(configCommon.getDatabaseType().getDatabaseMetaDataQuerySql())) {

            } else {
                java.sql.DatabaseMetaData metaData = connection.getMetaData();
                try (ResultSet resultSet = metaData.getPrimaryKeys(databaseName, schema, tableName)) {
                    while (resultSet.next()) {
                        PrimaryKeyMetaData primaryKey = new PrimaryKeyMetaData();
                        primaryKey.setPkName(resultSet.getString("PK_NAME"));
                        primaryKey.setColumnName(resultSet.getString("COLUMN_NAME"));
                        primaryKey.setKeySeq(resultSet.getInt("KEY_SEQ"));
                        temp.add(primaryKey);
                    }
                }
            }
        }
        ArrayList<PrimaryKeyMetaData> primaryKeys = new ArrayList<>(temp.size());
        temp.stream().sorted((a, b) -> a.getKeySeq() - b.getKeySeq()).forEachOrdered(p -> {
            primaryKeys.add(p);
        });
        return primaryKeys;
    }

    @Override
    public List<String> getDefaultColumnsResultSetMetaDataColumnLabels(String tableName)
            throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getDefaultColumnsResultSetMetaDataColumnLabels(configCommon.getSchema(), configCommon.getDatabase(),
                tableName);
    }

    @Override
    public List<String> getDefaultColumnsResultSetMetaDataColumnLabels(String schema, String databaseName,
            String tableName) throws SQLException {
        try (Connection connection = getConnection();
                ResultSet resultSet = connection.getMetaData().getColumns(databaseName, schema, tableName, "%");) {
            return getResultSetMetaDataColumnLabels(resultSet);
        }
    }

    @Override
    public List<String> getDefaultIndexesResultSetMetaDataColumnLabels(String tableName)
            throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getDefaultIndexesResultSetMetaDataColumnLabels(configCommon.getSchema(), configCommon.getDatabase(),
                tableName);
    }

    @Override
    public List<String> getDefaultIndexesResultSetMetaDataColumnLabels(String schema, String databaseName,
            String tableName) throws SQLException {
        try (Connection connection = getConnection();
                ResultSet indexInfo = connection.getMetaData().getIndexInfo(databaseName, schema, tableName, false,
                        false);) {
            return getResultSetMetaDataColumnLabels(indexInfo);
        }
    }

    @Override
    public List<String> getDefaultTableResultSetMetaDataColumnLabels(String tableName)
            throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getDefaultTableResultSetMetaDataColumnLabels(configCommon.getSchema(), configCommon.getDatabase(),
                tableName);
    }

    @Override
    public List<String> getDefaultTableResultSetMetaDataColumnLabels(String schema, String databaseName,
            String tableName) throws SQLException {
        try (Connection connection = getConnection();
                ResultSet tables = connection.getMetaData().getTables(databaseName, schema, tableName,
                        configCommon.getDatabaseType().getDatabaseTableTypes());) {
            return getResultSetMetaDataColumnLabels(tables);
        }
    }

    @Override
    public List<String> getResultSetMetaDataColumnLabels(ResultSet resultSet) throws SQLException {
        LinkedList<String> res = new LinkedList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            res.add(metaData.getColumnLabel(i));
        }
        return res;
    }

    @Override
    public List<String> getTableNames() throws UnknownDatabaseException, SQLException {
        if (!checkDatabaseNameConfigExists()) {
            throw new UnknownDatabaseException("连接信息中未指定获取哪个数据库");
        }
        return getTableNames(configCommon.getDatabase());
    }

    @Override
    public List<String> getTableNames(String databaseName) throws UnknownDatabaseException, SQLException {
        LinkedList<String> res = new LinkedList<>();
        try (Connection connection = getConnection();) {
            connection.setAutoCommit(false);
            connection.prepareStatement("use " + databaseName).execute();
            try (ResultSet executeQuery = connection.prepareStatement("show tables").executeQuery();) {
                while (executeQuery.next()) {
                    res.add(executeQuery.getString(1));
                }
            }
        }
        return res;
    }

    protected abstract boolean checkDatabaseNameConfigExists();

    protected ColumnMetaData readColumnMetaDataFromResultSet(ResultSet columns) throws SQLException {
        ColumnMetaData columnMetaData = new ColumnMetaData();
        columnMetaData.setName(columns.getString(ColumnInfoLabels.COLUMN_NAME));
        columnMetaData.setRemarks(columns.getString(ColumnInfoLabels.REMARKS));
        columnMetaData.setTypeName(columns.getString(ColumnInfoLabels.TYPE_NAME));
        columnMetaData.setLength(columns.getInt(ColumnInfoLabels.COLUMN_SIZE));
        columnMetaData.setDatatype(columns.getInt(ColumnInfoLabels.DATA_TYPE));
        columnMetaData.setOrdinalPosition(columns.getInt(ColumnInfoLabels.ORDINAL_POSITION));
        columnMetaData.setNullable(columns.getInt(ColumnInfoLabels.NULLABLE));
        columnMetaData.setDecimalDigits(columns.getInt(ColumnInfoLabels.DECIMAL_DIGITS));
        return columnMetaData;
    }

}
