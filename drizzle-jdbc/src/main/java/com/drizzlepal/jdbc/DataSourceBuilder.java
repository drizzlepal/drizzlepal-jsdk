package com.drizzlepal.jdbc;

import com.drizzlepal.jdbc.exception.JdbcException;

public class DataSourceBuilder {

    private final DefaultDatabaseConfig databaseConfig;

    private DataSourceBuilder(DatabaseType databaseType) {
        databaseConfig = databaseType.initDataConfig();
    }

    public static DataSourceBuilder builder(DatabaseType databaseType) {
        return new DataSourceBuilder(databaseType);
    }

    public DataSourceBuilder userName(String userName) {
        databaseConfig.setUsername(userName);
        return this;
    }

    public DataSourceBuilder password(String password) {
        databaseConfig.setPassword(password);
        return this;
    }

    public DataSourceBuilder host(String host) {
        databaseConfig.setHost(host);
        return this;
    }

    public DataSourceBuilder port(int port) {
        databaseConfig.setPort(port);
        return this;
    }

    public DataSourceBuilder database(String database) {
        databaseConfig.setDatabase(database);
        return this;
    }

    public DataSourceBuilder schema(String schema) {
        databaseConfig.setSchema(schema);
        return this;
    }

    public DataSourceBuilder connectionParam(String key, String value) {
        databaseConfig.getConnectionParams().put(key, value);
        return this;
    }

    public DataSourceBuilder maxActive(int maxActive) {
        databaseConfig.setMaxActive(maxActive);
        return this;
    }

    public DataSourceBuilder maxWaitMs(long maxWaitMs) {
        databaseConfig.setMaxWaitMs(maxWaitMs);
        return this;
    }

    public DataSourceBuilder minIdle(int minIdle) {
        databaseConfig.setMinIdle(minIdle);
        return this;
    }

    public DataSource build() throws JdbcException {
        databaseConfig.isValid();
        return databaseConfig.getDatabaseType().initDataSource(databaseConfig);
    }

}