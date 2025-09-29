package com.drizzlepal.jdbc.database.sqlserver;

import com.drizzlepal.jdbc.DefaultDatabaseConfig;
import com.drizzlepal.jdbc.DefaultDataSource;

public class DataSourceSqlServer extends DefaultDataSource {

    public DataSourceSqlServer(DefaultDatabaseConfig configCommon) {
        super(configCommon);
    }

    @Override
    public String buildJdbcUrl(DefaultDatabaseConfig configCommon) {
        return String.format("jdbc:sqlserver://%s:%s;databaseName=%s", configCommon.getHost(), configCommon.getPort(),
                configCommon.getDatabase());
    }

    @Override
    protected boolean checkDatabaseNameConfigExists() {
        return configCommon.getSchema() != null && !configCommon.getSchema().isEmpty()
                && configCommon.getDatabase() != null && !configCommon.getDatabase().isEmpty();
    }

}
