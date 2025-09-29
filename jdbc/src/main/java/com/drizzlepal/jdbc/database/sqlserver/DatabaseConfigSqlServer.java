package com.drizzlepal.jdbc.database.sqlserver;

import java.util.HashMap;

import com.drizzlepal.jdbc.DefaultDatabaseConfig;
import com.drizzlepal.jdbc.DatabaseType;

public class DatabaseConfigSqlServer extends DefaultDatabaseConfig {

    public DatabaseConfigSqlServer() {
        super(DatabaseType.SQL_SERVER, new HashMap<>());
    }

}
